import { Injectable } from '@angular/core';

const ACCESS_KEY = 'supportiq.accessToken';
const REFRESH_KEY = 'supportiq.refreshToken';

/**
 * Persistance des jetons. Le refresh token n'est jamais envoye en en-tete : il ne sert
 * qu'au endpoint /refresh. Choix assume : localStorage (simple, survit au rechargement) ;
 * la surface XSS est mitigee par l'absence d'inline scripts et le TTL court de l'access token.
 */
@Injectable({ providedIn: 'root' })
export class TokenStore {
  get accessToken(): string | null {
    return localStorage.getItem(ACCESS_KEY);
  }

  get refreshToken(): string | null {
    return localStorage.getItem(REFRESH_KEY);
  }

  set(accessToken: string, refreshToken: string): void {
    localStorage.setItem(ACCESS_KEY, accessToken);
    localStorage.setItem(REFRESH_KEY, refreshToken);
  }

  clear(): void {
    localStorage.removeItem(ACCESS_KEY);
    localStorage.removeItem(REFRESH_KEY);
  }
}
