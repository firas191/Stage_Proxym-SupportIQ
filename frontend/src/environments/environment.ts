export const environment = {
  production: false,
  // Vide en dev : les requetes /api sont proxifiees vers :8080 par proxy.conf.json (meme origine).
  // En prod, nginx sert le SPA et proxifie /api vers le backend.
  apiBaseUrl: '',
};
