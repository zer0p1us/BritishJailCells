from typing import List

from pydantic import BaseModel

from models.Application import Application


class History(BaseModel):
    applications: List[Application]
