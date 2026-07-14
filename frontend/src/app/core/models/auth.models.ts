export type Role = 'ADMIN' | 'MANAGER' | 'AGENT';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  fullName: string;
  role: Role;
}

export interface TokenResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
}

export interface MeResponse {
  email: string;
  role: Role;
}

export interface CurrentUser {
  email: string;
  role: Role;
}
