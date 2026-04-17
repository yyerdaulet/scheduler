from fastapi import FastAPI
from fastapi.staticfiles import StaticFiles
from pydantic import BaseModel
from typing import List
from groq import Groq
import json
import os

app = FastAPI()

client = Groq(api_key=os.environ["GROQ_API_KEY"])


class Link(BaseModel):
    teacher: str
    subject: str
    group: str
    hours: int


class ScheduleRequest(BaseModel):
    teachers: List[str]
    groups: List[str]
    rooms: List[str]
    links: List[Link]
    exceptions: List[str]


@app.post("/api/generate")
async def generate_schedule(req: ScheduleRequest):
    links_text = "\n".join([
        f"  - {l.teacher} | {l.subject} | {l.group} | {l.hours} ч подряд (1 раз в неделю)"
        for l in req.links
    ])
    exc_text = "\n".join([f"  - {e}" for e in req.exceptions]) if req.exceptions else "  - нет"

    prompt = f"""Ты — AI Scheduler. Составь учебное расписание на неделю строго по правилам.

ВХОДНЫЕ ДАННЫЕ:
Преподаватели: {', '.join(req.teachers)}
Группы: {', '.join(req.groups)}
Кабинеты: {', '.join(req.rooms)}

Связки (препод | предмет | группа | часов подряд за 1 день):
{links_text}

Исключения:
{exc_text}

ЖЁСТКИЕ ПРАВИЛА:
1. Дни недели: Понедельник, Вторник, Среда, Четверг, Пятница, Суббота
2. Временные слоты: 08:00, 09:00, 10:00, 11:00, 12:00, 13:00, 14:00, 15:00, 16:00, 17:00, 18:00
3. Каждая связка проводится ровно 1 раз в неделю — все часы ПОДРЯД блоком в один день
4. Один кабинет не может использоваться дважды в одно время
5. Один преподаватель не может вести два занятия одновременно
6. Одна группа не может быть на двух занятиях одновременно
7. Строго соблюдай все исключения
8. Распредели связки равномерно по разным дням недели
9. Для каждой связки назначь свободный кабинет из списка
10. Блок занятий идёт подряд без разрывов (2ч с 09:00 → слоты 09:00 и 10:00, time_end = 11:00)
11. Последний блок должен заканчиваться не позже 19:00

ФОРМАТ ОТВЕТА — только JSON без markdown:
{{
  "reasoning": ["шаг 1", "шаг 2", "..."],
  "schedule": [
    {{
      "day": "Понедельник",
      "slots": [
        {{
          "time_start": "hh:dd",
          "time_end": "hh:dd",
          "teacher": "...",
          "subject": "...",
          "group": "...",
          "room": "...",
          "hours": 2
        }}
      ]
    }}
  ],
  "warnings": []
}}

Верни ТОЛЬКО JSON."""

    response = client.chat.completions.create(
        model="llama-3.3-70b-versatile",
        messages=[{"role": "user", "content": prompt}],
        max_tokens=4000,
    )
    text = response.choices[0].message.content.strip()
    text = text.replace("```json", "").replace("```", "").strip()
    return json.loads(text)


@app.get("/api/health")
def health():
    return {"status": "ok"}


app.mount("/", StaticFiles(directory="static", html=True), name="static")