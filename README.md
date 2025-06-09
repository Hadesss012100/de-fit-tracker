# de-fit-tracker
 "Spring Boot API for calorie deficit and TDEE calculation"
# DeFit Tracker

**Spring Boot API for calorie deficit, TDEE, BMR, and body composition analysis**

##  Описание

Создай Spring Boot проект с названием **DeFit Tracker**, который по REST считает:

- BMR (базовый метаболизм)
- TDEE (общий расход энергии)
- TEF (термический эффект пищи)
- % жира
- Lean Body Mass
- ИМТ
- Общий дневной дефицит или профицит калорий

## 📥 Ввод (через JSON):
- рост (см)
- вес (кг)
- возраст
- пол (`male` или `female`)
- опыт в спорте (в годах)
- список еды (название + граммы)
- шаги за день

##  Результат (JSON):
- bmr, tdee, tef, fat %, lean mass, bmi, intake kcal, дефицит

---

##  Стек
- Java 17
- Spring Boot
- Lombok
- H2 Database
- Swagger / OpenAPI

---

##  Дополнительные задачи для реализации:

- [ ] Добавь в `foodIntake` автоматический расчёт калорий по встроенной базе продуктов
- [ ] Добавь сортировку по дефициту в JSON-ответе
- [ ] Добавь формулу Jackson & Pollock для процента жира
- [ ] Раздели DTO и сущности
- [ ] Сделай валидацию входных данных через `@Valid`
