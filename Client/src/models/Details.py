from pydantic import BaseModel


class Details(BaseModel):
    furnished: bool
    amenities: list[str]
    live_in_landlord: bool
    shared_with: int
    bills_included: bool
    bathroom_shared: bool
