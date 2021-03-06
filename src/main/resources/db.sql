create table if not exists credit_history
(
    id bigint generated by default as identity
        constraint credit_history_pkey
            primary key,
    amount double precision not null,
    delay_days integer not null,
    due_date date,
    paid_date date
);

alter table credit_history owner to postgres;

create table if not exists customer
(
    id bigint generated by default as identity
        constraint customer_pkey
            primary key,
    age integer not null,
    approx_monthly_payment double precision not null,
    full_name varchar(255)
);

alter table customer owner to postgres;

create table if not exists r_customer_credit_hist
(
    customer_id bigint
        constraint fkjtewj0rpb9hegdjj5r35fwpxh
            references customer,
    credit_id bigint not null
        constraint r_customer_credit_hist_pkey
            primary key
        constraint fkmdy0j73utymhq0bkn2ymw1lqo
            references credit_history
);

alter table r_customer_credit_hist owner to postgres;

create table if not exists xuser
(
    id integer generated by default as identity
        constraint xuser_pkey
            primary key,
    password varchar(255),
    roles varchar(255),
    username varchar(255)
);

alter table xuser owner to postgres;