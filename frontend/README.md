# Frontend — Angular 18 (généré au J4, semaine 1)

## Génération
```bash
npm i -g @angular/cli@18
ng new supportiq-frontend --directory . --routing --style scss --ssr false
npm i chart.js ng2-charts @stomp/stompjs
```

## Structure cible
```
src/app/
  core/        intercepteur JWT + refresh silencieux, guards, services API
  features/
    auth/      login, register
    dashboard/ cartes KPI, graphiques Chart.js, alertes temps réel
    tickets/   liste paginée serveur, filtres, fiche ticket, annotations
    insight/   chat manager (Text-to-SQL), rendu chart_spec
    admin/     utilisateurs, base de connaissances
  shared/      composants UI, pipes, directives
```

## Décision à documenter (ADR)
NgRx vs Angular signals pour l'état global — trancher au J4 et écrire `docs/adr/000X-etat-frontend.md`.
