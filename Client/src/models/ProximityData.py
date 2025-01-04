from pydantic import BaseModel


class ProximityData(BaseModel):
    duration_seconds: float
    distance_meters: float
