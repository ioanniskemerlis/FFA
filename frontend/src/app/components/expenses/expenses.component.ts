import { Component, OnInit } from '@angular/core';
import { ExpenseService } from '../../services/expense.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatOptionModule,
    MatSelectModule,
    MatPaginatorModule,
  ],
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.scss'],
})
export class ExpensesComponent implements OnInit {
  expenses: any[] = [];
  newExpense = { type: '', amount: 0, date: '', notes: '' };
  username: string = ''; // Define the username property
  expenseTypes: string[] = [
    'Tax',
    'Fuel',
    'Personal Expenses',
    'Tolls',
    'Equipment Service',
    'Insurance',
    'KTEO',
    'Road Stamp',
  ];

  // Pagination properties
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  constructor(private expenseService: ExpenseService, private router: Router) {}

  ngOnInit(): void {
    this.getUsernameFromToken();
    this.loadExpenses(this.pageIndex, this.pageSize);
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

  loadExpenses(page: number, size: number) {
    this.expenseService.getExpenses(page, size).subscribe({
      next: (response) => {
        this.expenses = response.content; // Paginated data
        this.totalElements = response.totalElements; // Total number of entries
      },
      error: (error) => console.error('Error loading expenses:', error),
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadExpenses(this.pageIndex, this.pageSize);
  }

  addExpense() {
    this.expenseService.addExpense(this.newExpense).subscribe({
      next: () => {
        alert('Expense added successfully!');
        this.newExpense = { type: '', amount: 0, date: '', notes: '' };
        this.loadExpenses(this.pageIndex, this.pageSize);
      },
      error: (error) => console.error('Error adding expense:', error),
    });
  }

  deleteExpense(id: string) {
    this.expenseService.deleteExpense(id).subscribe({
      next: () => {
        alert('Expense deleted successfully!');
        this.loadExpenses(this.pageIndex, this.pageSize);
      },
      error: (error) => console.error('Error deleting expense:', error),
    });
  }

  logout() {
    localStorage.removeItem('token'); // Clear the token
    this.router.navigate(['/auth']); // Redirect to login page
  }

  dashboard() {
    this.router.navigate(['/dashboard']);
  }
}
