-- create T_PROPERTY_META table
CREATE COLUMN TABLE "T_PROPERTY_META" 
(
"ID" BIGINT CS_FIXED,
"TENANT_ID" NVARCHAR(32),
"OBJECT_NAME" NVARCHAR(32),
"TYPE" NVARCHAR(32),
"INTERNAL_NAME" NVARCHAR(32),
"DISPLAY_NAME" NVARCHAR(32),
"PARAM_INDEX" INTEGER CS_INT,
"SYSTEM_FIELD" TINYINT CS_INT
) UNLOAD PRIORITY 5 AUTO MERGE;

-- create sequence
create sequence "T_PROPERTY_META_SEQ" increment by 1 start with 1 minvalue 1 maxvalue 4611686018427387903 no cycle;

-- insert data
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant004','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant005','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant006','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant007','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant008','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant009','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant010','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant011','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER','TIMESTAMP','ORDER_DATE' ,'ORDER_DATE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER_LINE','NVARCHAR','TENANT_ID' ,'TENANT_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER_LINE','NVARCHAR','ORDER_ID' ,'ORDER_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER_LINE','NVARCHAR','ITEM_ID' ,'ITEM_ID',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER_LINE','DECIMAL','ITEM_PRICE' ,'ITEM_PRICE',-1,1);
insert into T_PROPERTY_META("ID", "TENANT_ID", "OBJECT_NAME", "TYPE", "INTERNAL_NAME", "DISPLAY_NAME", "PARAM_INDEX", "SYSTEM_FIELD") values(T_PROPERTY_META_SEQ.nextval,'Tenant012','T_ORDER_LINE','DECIMAL','ITEM_QUANTITY' ,'ITEM_QUANTITY',-1,1);

CREATE COLUMN TABLE "T_ORDER" 
(
"TENANT_ID" NVARCHAR(32),
"ORDER_ID" NVARCHAR(32),
"ORDER_DATE" LONGDATE CS_LONGDATE,
PRIMARY KEY ("TENANT_ID","ORDER_ID")
) UNLOAD PRIORITY 5 AUTO MERGE;

CREATE COLUMN TABLE "T_ORDER_LINE" 
(
"TENANT_ID" NVARCHAR(32),
"ORDER_ID" NVARCHAR(32),
"ITEM_ID" NVARCHAR(32),
"ITEM_PRICE" DECIMAL(21,6) CS_FIXED,
"ITEM_QUANTITY" DECIMAL(21, 6) CS_FIXED,
"TOTAL" DECIMAL(21, 6) CS_FIXED,
PRIMARY KEY ("TENANT_ID","ORDER_ID","ITEM_ID")
) UNLOAD PRIORITY 5 AUTO MERGE;

alter table T_ORDER add (id bigint);
create sequence "T_ORDER_SEQ" increment by 1 start with 1 minvalue 1 maxvalue 4611686018427387903 no cycle;
UPDATE T_ORDER SET ID = T_ORDER_SEQ.nextval;

alter table T_ORDER_LINE add (id bigint);
create sequence "T_ORDER_LINE_SEQ" increment by 1 start with 1 minvalue 1 maxvalue 4611686018427387903 no cycle;
UPDATE T_ORDER_LINE SET ID = T_ORDER_LINE_SEQ.nextval;

create column table "T_PROPERTY_COUNTING"
(
"ID" BIGINT null,
"OBJECT_NAME" NVARCHAR (32) null,
"FIELD_NAME" NVARCHAR (32) null,
"COUNTING" BIGINT null default 0
);

create sequence "T_PROPERTY_COUNTING_SEQ" increment by 1 start with 1 minvalue 1 maxvalue 4611686018427387903 no cycle;

insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER', 'TENANT_ID', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER', 'ORDER_ID', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER', 'ORDER_DATE', 9);

insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER_LINE', 'TENANT_ID', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER_LINE', 'ORDER_ID', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER_LINE', 'ITEM_ID', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER_LINE', 'ITEM_PRICE', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER_LINE', 'ITEM_QUANTITY', 9);
insert into T_PROPERTY_COUNTING("ID", "OBJECT_NAME", "FIELD_NAME", "COUNTING") values(T_PROPERTY_COUNTING_SEQ.nextval, 'T_ORDER', 'ORDER_DATE', 9);

-- CREATE VIEW 
create view T_ORDER_TENANT004_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT004_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT005_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT005_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT006_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT006_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT007_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT007_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT008_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT008_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT009_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT009_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT010_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT010_VIEW as select * from t_order_LINE;


create view T_ORDER_TENANT011_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT011_VIEW as select * from t_order_LINE;

create view T_ORDER_TENANT012_VIEW as select * from t_order;
create view T_ORDER_LINE_TENANT012_VIEW as select * from t_order_LINE;;