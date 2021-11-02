create table hr_payroll.worker
(
    id           bigserial not null,
    daily_income float     not null,
    active       bool      not null,

    primary key (id)
);