
---------------------------------getWeeklyReportData---------------------------------
select 
h.regionCenter as name,
'RSD' as level,
IFNULL(rhinocort_data.inRate,0) as inRate,
IFNULL(rhinocort_data.num1,0) as num1,
IFNULL(rhinocort_data.num2,0) as num2,
IFNULL(rhinocort_data.num3,0) as num3,
IFNULL(rhinocort_data.num4,0) as num4,
IFNULL(rhinocort_data.num5,0) as num5 
from (
	select h.regionCenter,
	count(1) / (
		select count(1) 
		from tbl_hospital h1 
		where h1.isKPI='1' 
		and h1.regionCenter = h.regionCenter 
	) as inRate,
	(IFNULL(sum(num1),0)/count(1))*5 as num1,
	(IFNULL(sum(num2),0)/count(1))*5 as num2,
	(IFNULL(sum(num3),0)/count(1))*5 as num3,
	(IFNULL(sum(num4),0)/count(1))*5 as num4,
	(IFNULL(sum(num5),0)/count(1))*5 as num5 
	from tbl_rhinocort_data rd, tbl_hospital h
	where rd.createdate between '2014-04-14' and '2014-04-21' 
	and rd.hospitalCode = h.code 
	and h.isKPI='1' 
	group by h.regionCenter 
) rhinocort_data 
right join tbl_hospital h on rhinocort_data.regionCenter = h.regionCenter 
group by h.regionCenter 
order by h.regionCenter;



------------------------------------getRSMWeeklyReportData-----------------------

select 
h.regionCenter, 
h.region as name,
'RSM' as level,
(select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.region = h.region ) as hosNum, 
IFNULL(rhinocort_data.inRate,0) as inRate,
IFNULL(rhinocort_data.num1,0) as num1,
IFNULL(rhinocort_data.num2,0) as num2,
IFNULL(rhinocort_data.num3,0) as num3,
IFNULL(rhinocort_data.num4,0) as num4,
IFNULL(rhinocort_data.num5,0) as num5 
from (
	select h.region,
	count(1) / (select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.region = h.region ) as inRate,
	(IFNULL(sum(num1),0)/count(1))*5 as num1,
	(IFNULL(sum(num2),0)/count(1))*5 as num2,
	(IFNULL(sum(num3),0)/count(1))*5 as num3,
	(IFNULL(sum(num4),0)/count(1))*5 as num4,
	(IFNULL(sum(num5),0)/count(1))*5 as num5 
	from tbl_rhinocort_data rd, tbl_hospital h 
	where rd.createdate between '2014-04-14' and '2014-04-21' 
	and rd.hospitalCode = h.code 
	and h.regionCenter = 'North GRA' 
	and h.isKPI='1' 
	group by h.region 
) rhinocort_data 
right join tbl_hospital h on rhinocort_data.region = h.region 
where h.regionCenter = 'North GRA' 
and h.isKPI='1' 
group by h.region 
order by h.region;

------------------------------------------------------getREPWeeklyReportData---------------------------------
select 
u.region,
u.userCode,
IFNULL(u.name,'vacant') as name,
'REP' as level,
(select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.region = u.region and h1.saleCode = u.userCode ) as hosNum, 
IFNULL(rhinocort_data.inRate,0) as inRate,
IFNULL(rhinocort_data.num1,0) as num1,
IFNULL(rhinocort_data.num2,0) as num2,
IFNULL(rhinocort_data.num3,0) as num3,
IFNULL(rhinocort_data.num4,0) as num4,
IFNULL(rhinocort_data.num5,0) as num5 
from (
	select h.region, h.saleCode, h.isKPI, 
	count(1) / (select count(1) from tbl_hospital h1 where h1.isKPI='1' and h1.region = h.region and h1.saleCode = h.saleCode ) as inRate,
	(IFNULL(sum(num1),0)/count(1))*5 as num1,
	(IFNULL(sum(num2),0)/count(1))*5 as num2,
	(IFNULL(sum(num3),0)/count(1))*5 as num3,
	(IFNULL(sum(num4),0)/count(1))*5 as num4,
	(IFNULL(sum(num5),0)/count(1))*5 as num5 
	from tbl_rhinocort_data rd, tbl_hospital h 
	where rd.createdate between '2014-04-14' and '2014-04-21' 
	and rd.hospitalCode = h.code 
	and h.region = 'BJ RE' 
	and h.isKPI='1' 
	group by h.region, h.saleCode 
) rhinocort_data 
right join tbl_userinfo u on rhinocort_data.saleCode = u.userCode and rhinocort_data.region = u.region 
where u.region = 'BJ RE' 
and exists (select 1 from tbl_hospital h where u.userCode = h.saleCode and h.isKPI='1') 
group by u.userCode 
order by u.userCode;
