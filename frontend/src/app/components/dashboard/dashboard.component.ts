import { Component, OnInit, ViewChild } from '@angular/core';
import { IncomeService } from '../../services/income.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ExpenseService } from '../../services/expense.service';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { jwtDecode } from 'jwt-decode';
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
// Import required Chart.js modules
import {
  Chart,
  ArcElement,
  Tooltip,
  Legend,
  PieController,
} from 'chart.js';

// Register the required modules
Chart.register(PieController, ArcElement, Tooltip, Legend);

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        CommonModule,
        MatToolbarModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule, // Import MatInputModule
        MatButtonModule,
        FormsModule,
        MatPaginatorModule,
        BaseChartDirective
        ],
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  totalIncome = 0;
  totalExpenses = 0;
  netProfit = 0;
  incomes: any[] = [];
  expenses: any[] = [];
  paginatedIncomes: any[] = [];
  paginatedExpenses: any[] = [];
  pageSize = 10;
  incomesLength = 0; // Total number of income entries
  expensesLength = 0; // Total number of expense entries
  username: string = ''; // Define the username property

  // Chart Data
  public pieChartLabels: string[] = ['Incomes', 'Expenses'];
  public pieChartData: ChartConfiguration<'pie'>['data'] = {
    labels: ['Incomes', 'Expenses'],
    datasets: [
      {
        data: [0, 0], // Default data
        backgroundColor: ['#4CAF50', '#F44336'], // Green and Red
        hoverBackgroundColor: ['#66BB6A', '#EF5350'], // Hover colors
        borderColor: ['#FFFFFF', '#FFFFFF'], // Border color for slices
        borderWidth: 2, // Border width for slices
        hidden: false, // Ensure the dataset is visible by default
      },
    ],
  };
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      tooltip: {
        callbacks: {
          label: (tooltipItem) => {
            const value = tooltipItem.raw as number; // Extract raw value
            return `€${value.toFixed(2)}`; // Format value with euro sign
          },
        },
      },
    },
  };
  public pieChartType: 'pie' = 'pie';

  constructor(
    private incomeService: IncomeService,
    private expenseService: ExpenseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadIncomes();
    this.loadExpenses();
    this.getUsernameFromToken();
    this.calculateNetProfit();
  }

  getUsernameFromToken() {
    const token = localStorage.getItem('token'); // Retrieve token from localStorage
    if (token) {
      const decodedToken: any = jwtDecode(token); // Decode the token
      this.username = decodedToken?.sub || 'User'; // Extract the username (usually in `sub` claim)
    } else {
      this.router.navigate(['/auth']); // Redirect to login if no token is found
    }
  }

  loadIncomes() {
    this.incomeService.getIncomes().subscribe({
      next: (incomes) => {
        this.incomes = incomes.sort((a: any, b: any) => new Date(b.date).getTime() - new Date(a.date).getTime());
        this.incomesLength = this.incomes.length; // Set the total length for paginator
        this.totalIncome = this.incomes.reduce((sum: number, income: any) => sum + income.amount, 0);
        this.updatePaginatedIncomes(0, this.pageSize);
        this.calculateNetProfit();
        this.updateChartData();
      },
      error: (err) => console.error('Error loading incomes:', err),
    });
  }
  
  loadExpenses() {
    this.expenseService.getExpenses().subscribe({
      next: (expenses) => {
        this.expenses = expenses.sort((a: any, b: any) => new Date(b.date).getTime() - new Date(a.date).getTime());
        this.expensesLength = this.expenses.length; // Set the total length for paginator
        this.totalExpenses = this.expenses.reduce((sum: number, expense: any) => sum + expense.amount, 0);
        this.updatePaginatedExpenses(0, this.pageSize);
        this.calculateNetProfit();
        this.updateChartData();
      },
      error: (err) => console.error('Error loading expenses:', err),
    });
  }

  updatePaginatedIncomes(startIndex: number, endIndex: number) {
    this.paginatedIncomes = this.incomes.slice(startIndex, endIndex);
  }

  updatePaginatedExpenses(startIndex: number, endIndex: number) {
    this.paginatedExpenses = this.expenses.slice(startIndex, endIndex);
  }

  onIncomePageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    this.updatePaginatedIncomes(startIndex, endIndex);
  }
  
  onExpensePageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    this.updatePaginatedExpenses(startIndex, endIndex);
  }

  calculateNetProfit() {
    this.netProfit = this.totalIncome - this.totalExpenses;
  }

  navigateToIncomes() {
    this.router.navigate(['/incomes']);
  }

  navigateToExpenses() {
    this.router.navigate(['/expenses']);
  }
  updateChartData() {
    this.pieChartData.datasets[0].data = [this.totalIncome, this.totalExpenses];
    this.chart?.update(); // Trigger chart update
  }

  logout() {
    localStorage.removeItem('token'); // Clear the token
    this.router.navigate(['/auth']); // Redirect to login page
  }
}