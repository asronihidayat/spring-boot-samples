-- ----------------------------
-- Table structure for cart
-- ----------------------------
CREATE TABLE "cart" (
  "id" BIGSERIAL PRIMARY KEY ,
  "user_id" int4,
  "total" int4,
  "code" varchar(255) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6),
  "updated_at" timestamp(6)
);
