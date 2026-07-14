from fastapi.testclient import TestClient

from app.main import app

client = TestClient(app)


def test_health():
    resp = client.get("/health")
    assert resp.status_code == 200
    assert resp.json()["status"] == "ok"


def test_ready_reports_database_status():
    # Sans base joignable (cas CI unitaire), la readiness renvoie 503 ; sinon 200.
    resp = client.get("/health/ready")
    assert resp.status_code in (200, 503)
    assert "database" in resp.json()
