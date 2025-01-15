import { Component, OnInit } from '@angular/core';
import { IncomeService } from '../../services/income.service';
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
  selector: 'app-incomes',
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
  templateUrl: './incomes.component.html',
  styleUrls: ['./incomes.component.scss'],
})
export class IncomesComponent implements OnInit {
  incomes: any[] = [];
  newIncome = { type: '', amount: 0, date: '', notes: '' };
  username: string = '';
  incomeTypes: string[] = ['Daily Wage', 'Tips', 'Monthly Salary'];

  // Pagination properties
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  constructor(private incomeService: IncomeService, private router: Router) {}

  ngOnInit(): void {
    this.getUsernameFromToken();
    this.loadIncomes(this.pageIndex, this.pageSize);
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

  loadIncomes(page: number, size: number) {
    this.incomeService.getIncomes(page, size).subscribe({
      next: (response) => {
        this.incomes = response.content; // Paginated data
        this.totalElements = response.totalElements; // Total entries count
      },
      error: (error) => console.error('Error loading incomes:', error),
    });
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadIncomes(this.pageIndex, this.pageSize);
  }

  addIncome() {
    this.incomeService.addIncome(this.newIncome).subscribe({
      next: () => {
        alert('Income added successfully!');
        this.newIncome = { type: '', amount: 0, date: '', notes: '' };
        this.loadIncomes(this.pageIndex, this.pageSize);
      },
      error: (error) => console.error('Error adding income:', error),
    });
  }

  deleteIncome(id: string) {
    this.incomeService.deleteIncome(id).subscribe({
      next: () => {
        alert('Income deleted successfully!');
        this.loadIncomes(this.pageIndex, this.pageSize);
      },
      error: (error) => console.error('Error deleting income:', error),
    });
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/auth']);
  }

  dashboard() {
    this.router.navigate(['/dashboard']);
  }
}
