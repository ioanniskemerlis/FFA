import { Component, OnInit, ViewChild } from '@angular/core';
import { IncomeService } from '../../services/income.service';
import { ExpenseService } from '../../services/expense.service';
import { Router } from '@angular/router';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { jwtDecode } from 'jwt-decode';
import { ChartConfiguration, ChartOptions, ChartType } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
// Import required Chart.js modules
import { Chart, ArcElement, Tooltip, Legend, PieController } from 'chart.js';
import { CommonModule } from '@angular/common';
import { MatToolbar, MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

Chart.register(PieController, ArcElement, Tooltip, Legend);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [MatPaginatorModule,
    BaseChartDirective,
    CommonModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    FormsModule,
      ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;
  totalIncome = 0;
  totalExpenses = 0;
  netProfit = 0;
  incomes: any[] = [];
  expenses: any[] = [];
  username: string = '';

  // Flags to track when calculations are complete
  incomeCalculationDone = false;
  expenseCalculationDone = false;

  // Pagination properties for incomes and expenses
  incomePageIndex = 0;
  incomePageSize = 10;
  incomeTotalItems = 0;

  expensePageIndex = 0;
  expensePageSize = 10;
  expenseTotalItems = 0;

  // Chart Data
  public pieChartLabels: string[] = ['Incomes', 'Expenses'];
  public pieChartData: ChartConfiguration<'pie'>['data'] = {
    labels: ['Incomes', 'Expenses'],
    datasets: [
      {
        data: [0, 0],
        backgroundColor: ['#4CAF50', '#F44336'], // Green and Red
        hoverBackgroundColor: ['#66BB6A', '#EF5350'], // Hover colors
      },
    ],
  };
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: true,
    plugins: {
      legend: { position: 'top' },
    },
  };
  public pieChartType: 'pie' = 'pie';

  constructor(
    private incomeService: IncomeService,
    private expenseService: ExpenseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getUsernameFromToken();
    this.calculateTotalIncome();
    this.calculateTotalExpenses();
    this.loadIncomes(this.incomePageIndex, this.incomePageSize);
    this.loadExpenses(this.expensePageIndex, this.expensePageSize);
  }

  getUsernameFromToken() {
    const token = localStorage.getItem('token');
    if (token) {
      const decodedToken: any = jwtDecode(token);
      this.username = decodedToken?.sub || 'User';
    } else {
      this.router.navigate(['/auth']);
    }
  }

  calculateTotalIncome() {
    this.incomeService.getAllIncomes().subscribe({
      next: (allIncomes) => {
        this.totalIncome = allIncomes.reduce(
          (sum: number, income: any) => sum + income.amount,
          0
        );
        this.incomeCalculationDone = true;
        this.updateNetProfitAndChart();
      },
      error: (err) => console.error('Error calculating total income:', err),
    });
  }

  calculateTotalExpenses() {
    this.expenseService.getAllExpenses().subscribe({
      next: (allExpenses) => {
        this.totalExpenses = allExpenses.reduce(
          (sum: number, expense: any) => sum + expense.amount,
          0
        );
        this.expenseCalculationDone = true;
        this.updateNetProfitAndChart();
      },
      error: (err) => console.error('Error calculating total expenses:', err),
    });
  }

  updateNetProfitAndChart() {
    if (this.incomeCalculationDone && this.expenseCalculationDone) {
      this.netProfit = this.totalIncome - this.totalExpenses;
      this.updateChartData();
    }
  }

  updateChartData() {
    this.pieChartData.datasets[0].data = [this.totalIncome, this.totalExpenses];
    this.chart?.update();
  }

  loadIncomes(page: number, size: number) {
    this.incomeService.getIncomes(page, size).subscribe({
      next: (response) => {
        this.incomes = response.content;
        this.incomeTotalItems = response.totalElements;
      },
      error: (err) => console.error('Error loading incomes:', err),
    });
  }

  loadExpenses(page: number, size: number) {
    this.expenseService.getExpenses(page, size).subscribe({
      next: (response) => {
        this.expenses = response.content;
        this.expenseTotalItems = response.totalElements;
      },
      error: (err) => console.error('Error loading expenses:', err),
    });
  }

  onIncomePageChange(event: PageEvent): void {
    this.incomePageIndex = event.pageIndex;
    this.incomePageSize = event.pageSize;
    this.loadIncomes(this.incomePageIndex, this.incomePageSize);
  }

  onExpensePageChange(event: PageEvent): void {
    this.expensePageIndex = event.pageIndex;
    this.expensePageSize = event.pageSize;
    this.loadExpenses(this.expensePageIndex, this.expensePageSize);
  }

  navigateToIncomes() {
    this.router.navigate(['/incomes']);
  }

  navigateToExpenses() {
    this.router.navigate(['/expenses']);
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/auth']);
  }
}