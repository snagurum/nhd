select to_char(to_date(trim(price_date),'dd-mon-yyyy'),'yyyy'), count(*) from load_stock_price where trim(price_date) <> 'Date' group by 1;
select is_loaded, count(*) from load_bulk_stock_dates group by 1;
select name, type, cron_exp, job, total_loads, pending_loads, status  from cronjobs;
select to_char(start_date,'yyyy'), is_loaded, count(*) from load_bulk_stock_dates group by 1, 2 order by 1 desc,2;
select job_name, start_date, end_date from audit where job_name like 'loadStock%';

rm -rf /app/momentum
mkdir /app/momentum /app/momentum/logs /app/momentum/scripts /app/momentum/output
cp -r suppl /app/momentum
cp -r files /app/momentum
cp -R roles /app/momentum
cp -R install.yml /app/momentum
cp -R environments /app/momentum

ansible-playbook -i environments/local install.yml --tag setup
ansible-playbook -i environments/local install.yml --tag cronjobs

select current_timestamp;
select is_loaded, count(*) from load_bulk_stock_dates group by 1;
select to_char(start_date,'yyyy'), is_loaded, count(*) from load_bulk_stock_dates group by 1, 2 order by 1 desc,2;

