from pydantic import BaseModel


class Coordinates(BaseModel):
    latitude: float
    longitude: float

    def __str__(self) -> str:
        return f"latitude: {self.latitude}, longitude: {self.longitude}"
