from fastapi import APIRouter

from app.schemas import AnalyzeRequest, AnalysisResult

router = APIRouter()


@router.get("/health")
async def health() -> dict:
    return {"status": "ok", "service": "supportiq-ai"}


@router.post("/analyze", response_model=AnalysisResult)
async def analyze(req: AnalyzeRequest) -> AnalysisResult:
    from app.pipeline.triage import analyze as run

    return await run(req)
