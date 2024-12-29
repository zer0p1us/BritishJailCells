from pydantic import BaseModel


class Details(BaseModel):
    furnished: bool
    amenities: list[str]
    live_in_landlord: bool
    shared_with: int
    bills_included: bool
    bathroom_shared: bool

    def get_amenities(self) -> str:
        return ", ".join(str(amenity) for amenity in self.amenities)
