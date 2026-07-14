from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.api.routes import router
from app.core import db


@asynccontextmanager
async def lifespan(app: FastAPI):
    # Cycle de vie : ouverture du pool PostgreSQL au demarrage, fermeture propre a l'arret.
    await db.connect()
    try:
        yield
    finally:
        await db.disconnect()


app = FastAPI(title="SupportIQ — AI Service", version="0.1.0", lifespan=lifespan)
app.include_router(router)
