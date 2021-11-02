create table hr_payroll.payment
(
    id           bigserial not null,
    daily_income float     not null,
    worked_days  integer   not null,
    created_at   timestamp not null,
    worker_id    bigserial not null,

    primary key (id),
    foreign key (worker_id) references hr_payroll.worker (id)
);