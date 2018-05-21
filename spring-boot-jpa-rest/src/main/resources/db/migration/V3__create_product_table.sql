-- ----------------------------
-- Table structure for product
-- ----------------------------
CREATE TABLE "product" (
  "id" BIGSERIAL PRIMARY KEY ,
  "sku" varchar(45) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "thumbail" varchar(255) COLLATE "pg_catalog"."default",
  "stock_available" int4,
  "price" int4,
  "created_at" timestamp(6),
  "updated_at" timestamp(6)
);
