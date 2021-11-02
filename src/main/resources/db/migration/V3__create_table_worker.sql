create table hr_payroll.worker
(
    id           bigserial not null,
    daily_income float     not null,
    payment_id   bigserial not null,
    active       bool      not null,

    primary key (id),
    foreign key (payment_id) references hr_payroll.payment (id)
);