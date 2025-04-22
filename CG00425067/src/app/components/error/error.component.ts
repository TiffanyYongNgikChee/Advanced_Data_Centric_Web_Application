import { Component,inject } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';

@Component({
  selector: 'app-error',
  imports: [],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css'
})
export class ErrorComponent {
  errorMessage: string = 'An unexpected error occurred.';
  private router = inject(Router);

  constructor(private route: ActivatedRoute) {
    
    this.route.queryParams.subscribe(params => {
      this.errorMessage = params['message'] || this.errorMessage;
    });
  }

  goBack() {
    this.router.navigate(['/vehicles']);
  }
}