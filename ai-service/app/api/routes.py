from fastapi import APIRouter, Response, status

from app.core import db
from app.schemas import AnalyzeRequest, AnalysisResult

router = APIRouter()


@router.get("/health")
async def health() -> dict:
    """Liveness : le process repond (independant de la base)."""
    return {"status": "ok", "service": "supportiq-ai"}


@router.get("/health/ready")
async def ready(response: Response) -> dict:
    """Readiness : la base est joignable. 503 si down (utile pour l'orchestration)."""
    db_up = await db.ping()
    if not db_up:
        response.status_code = status.HTTP_503_SERVICE_UNAVAILABLE
    return {"status": "ready" if db_up else "unavailable", "database": "up" if db_up else "down"}


@router.post("/analyze", response_model=AnalysisResult)
async def analyze(req: AnalyzeRequest) -> AnalysisResult:
    from app.pipeline.triage import analyze as run

    return await run(req)
