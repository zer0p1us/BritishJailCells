from pydantic import BaseModel


class WeatherData(BaseModel):
    current_temp_celsius: float
    seven_day_average_temp_celsius: float
