import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient} from '@angular/common/http'; // Correct provider for HttpClient
import { FormsModule } from '@angular/forms';
import { importProvidersFrom } from '@angular/core';
import { appRoutes } from './app.routes';


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(appRoutes),
    provideHttpClient(), // Provide HttpClient
    importProvidersFrom(FormsModule), // Provide FormsModule
  ],
};

