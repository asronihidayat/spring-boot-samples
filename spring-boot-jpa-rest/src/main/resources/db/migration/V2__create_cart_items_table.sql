-- ----------------------------
-- Table structure for cart_items
-- ----------------------------
CREATE TABLE "cart_items" (
  "id" BIGSERIAL PRIMARY KEY,
  "product_id" int8,
  "cart_id" int8,
  "quantity" int4,
  "created_at" timestamp(6),
  "updated_at" timestamp(6)
);