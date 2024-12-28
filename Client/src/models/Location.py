from pydantic import BaseModel


class Location(BaseModel):
    city: str
    county: str
    postcode: str

    def __str__(self) -> str:
        return f"{self.city}, {self.county}, {self.postcode}"
