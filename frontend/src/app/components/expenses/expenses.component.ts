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
  username: string = '';
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

  isEditing: boolean = false; // Indicates if the form is in editing mode
  currentEditingId: string | null = null; // Stores the ID of the expense being edited

  constructor(private expenseService: ExpenseService, private router: Router) {}

  ngOnInit(): void {
    this.getUsernameFromToken();
    this.loadExpenses(this.pageIndex, this.pageSize);
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

  loadExpenses(page: number, size: number) {
    this.expenseService.getExpenses(page, size).subscribe({
      next: (response) => {
        this.expenses = response.content;
        this.totalElements = response.totalElements;
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
    if (this.isEditing) {
      // Update mode
      this.expenseService.updateExpense(this.currentEditingId!, this.newExpense).subscribe({
        next: () => {
          alert('Expense updated successfully!');
          this.resetForm();
          this.loadExpenses(this.pageIndex, this.pageSize);
        },
        error: (error) => console.error('Error updating expense:', error),
      });
    } else {
      // Add mode
      this.expenseService.addExpense(this.newExpense).subscribe({
        next: () => {
          alert('Expense added successfully!');
          this.resetForm();
          this.loadExpenses(this.pageIndex, this.pageSize);
        },
        error: (error) => console.error('Error adding expense:', error),
      });
    }
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

  editExpense(expense: any) {
    this.newExpense = { ...expense }; // Prefill the form with the expense details
    this.isEditing = true;
    this.currentEditingId = expense.id; // Store the expense ID
  }

  resetForm() {
    this.newExpense = { type: '', amount: 0, date: '', notes: '' };
    this.isEditing = false;
    this.currentEditingId = null;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/auth']);
  }

  dashboard() {
    this.router.navigate(['/dashboard']);
  }
}
