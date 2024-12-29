from datetime import date

from pydantic import BaseModel

from models.Location import Location
from models.Details import Details
from models.Coordinates import Coordinates
from models.WeatherData import WeatherData


class Room(BaseModel):
    id: int
    name: str
    location: Location
    details: Details
    price_per_month_gbp: int
    availability_date: date
    spoken_languages: list[str]
    coordinates: Coordinates
    weather_data: WeatherData
