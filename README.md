# Проект по автоматизации тестирования сайта reqres.in
## 📑Содержание
- [Стек проекта]
- [Текущее тестовое покрытие]
- [Запуск тестов]
- [Пример Allure-отчёта по пройденным тестам]

## ⚙️ Стек проекта

<p align="center">
<img width="6%" title="IntelliJ IDEA" src="Images/Intelij_IDEA.svg">
<img width="6%" title="Java" src="Images/Java.svg">
<img width="6%" title="Allure Report" src="Images/Allure_Report.svg">
<img width="6%" title="Gradle" src="Images/Gradle.svg">
<img width="6%" title="GitHub" src="Images/GitHub.svg">
<img width="6%" title="Jenkins" src="Images/Jenkins.svg">
<img width="6%" title="Telegram" src="Images/Telegram.svg">
<img width="6%" title="REST-Assured" src="Images/REST.png">
</p>

## ▶️ Текущее тестовое покрытие

**API автотесты:**

:white_check_mark: Получение данных о существующем пользователе
:white_check_mark: Попытка получения данных о не существующем в БД пользователе
:white_check_mark: Успешная регистрация
:white_check_mark: Попытка регистрации без пароля - проверка ошибки
:white_check_mark: Попытка регистрации без email - проверка ошибки
:white_check_mark: Удаление пользователя
:white_check_mark: Обновление данных пользователя

Дополнительно в тестах применены модели Pojo и Lamboc, спецификации вынесены в отдельный класс.

## 🧮 Запуск тестов

**Локально** 

Запуск всех тестов осуществляется командой в терминале

```bash  
gradle clean reqres_test
```

**Jenkins**

Для проекта сделана [сборка в Jenkins](https://jenkins.autotests.cloud/job/014-Ir4fin-%20graduation_project_api_reqres/) со следующими параметрами:

```bash
clean
${TASK_NAME}
```

## 📊 Пример Allure-отчета по пройденным тестам

Для каждого теста отображаются данные, которые он отправляет посредством API.

