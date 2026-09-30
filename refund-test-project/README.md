# Order Refund Interview Project

## What this is

A small existing e-commerce backend service (Spring Boot 3 / Java 17 / H2 in-memory DB) with orders, customers, and payments already implemented. You will be extending it during this session.

You have full access to Google, ChatGPT, Copilot, Claude, or any other tool you normally use. Use them however you'd use them on the job — we're interested in how you work, not whether you can memorize APIs.

## Getting started

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`. H2 console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:orderdb`, user `sa`, blank password).

Seed data (3 customers, 3 orders, 2 payments) loads automatically on startup — see `src/main/resources/data.sql`.

Existing endpoints:

- `POST /api/orders` — create an order
- `GET /api/orders/{id}` — fetch an order
- `GET /api/orders` — list orders
- `POST /api/orders/{id}/cancel` — cancel an order (triggers a full refund)
- `GET /api/payments/order/{orderId}` — get payment for an order
- `POST /api/payments/order/{orderId}/pay` — record a payment against an order

Run existing tests with:

```bash
mvn test
```

## The task

**Business ask:** *"We want to support partial refunds on an order. Add this capability to the system."*

That's the full brief you're going to get from "the business" — deliberately. Before writing code, take a few minutes to clarify scope with your interviewer as you would with a product owner: ask whatever you think is relevant to build this well. Not all questions will have a pre-scripted answer — some are there to see how you reason about ambiguity, so it's fine to state an assumption and move on if you don't get a crisp answer.

Roughly, you'll be expected to:

1. Understand how orders and payments currently work in this codebase.
2. Propose an API design for partial refunds (endpoint shape, request/response, status codes, error cases).
3. Implement it — entity/schema changes, service logic, controller, and reasonable validation — following the conventions you observe in the existing code.
4. Be ready to handle a change in requirements or a bug report partway through.
5. Add tests for the behavior you build.

There's no single "correct" solution. We're evaluating your approach, your design judgment, your code quality, and how you use whatever tools (AI or otherwise) you reach for along the way.

## A few ground rules

- Feel free to refactor existing code if you think it's warranted — just be ready to explain why.
- Partial implementations are fine if you run out of time. Talk through what you'd do next.
- Ask questions out loud whenever something is unclear. That's expected and encouraged.

Good luck.
