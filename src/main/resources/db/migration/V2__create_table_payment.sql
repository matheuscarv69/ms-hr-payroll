create table hr_payroll.payment
(
    id           bigserial not null,
    daily_income float     not null,
    worked_days  integer   not null,
    created_at   timestamp not null,

    primary key (id)
);