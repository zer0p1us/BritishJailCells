from typing import List

from pydantic import BaseModel

from models.Room import Room


class Rooms(BaseModel):
    rooms: List[Room]
