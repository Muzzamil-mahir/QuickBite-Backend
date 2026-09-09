# API Controller Organization

## RestaurantController

### GET
- `/restaurants/search` (PUBLIC)
- `/restaurants/{id}` (PUBLIC)
- `/restaurants/search/autocomplete` (PUBLIC)
- `/restaurants/my` (RESTAURANT_OWNER)
- `/restaurants/{id}/hours` (RESTAURANT_OWNER)
- `/restaurants/{id}/earnings` (RESTAURANT_OWNER)

### POST
- `/restaurants` (RESTAURANT_OWNER)

### PUT
- `/restaurants/{id}` (RESTAURANT_OWNER)
- `/restaurants/{id}/hours` (RESTAURANT_OWNER)

### PATCH
- `/restaurants/{id}/availability` (RESTAURANT_OWNER)

---

## OrderController

### GET
- `/restaurants/{id}/orders` (RESTAURANT_OWNER)

### POST
- `/restaurants/{id}/orders/{orderId}/confirm` (RESTAURANT_OWNER)
- `/restaurants/{id}/orders/{orderId}/reject` (RESTAURANT_OWNER)
- `/restaurants/{id}/orders/{orderId}/ready` (RESTAURANT_OWNER)

---

## MenuController

### GET
- `/restaurants/{id}/menu` (PUBLIC)

### POST
- `/restaurants/{id}/categories` (RESTAURANT_OWNER)
- `/restaurants/{id}/items` (RESTAURANT_OWNER)

### PUT
- `/restaurants/{id}/categories/{catId}` (RESTAURANT_OWNER)
- `/restaurants/{id}/items/{itemId}` (RESTAURANT_OWNER)

### DELETE
- `/restaurants/{id}/categories/{catId}` (RESTAURANT_OWNER)
- `/restaurants/{id}/items/{itemId}` (RESTAURANT_OWNER)

### PATCH
- `/restaurants/{id}/categories/{catId}/availability` (RESTAURANT_OWNER)
- `/restaurants/{id}/items/{itemId}/availability` (RESTAURANT_OWNER)

---

## MediaController

### POST
- `/restaurants/{id}/items/{itemId}/photos/presign` (RESTAURANT_OWNER)
- `/restaurants/{id}/items/{itemId}/photos/confirm` (RESTAURANT_OWNER)

### DELETE
- `/restaurants/{id}/items/{itemId}/photos/{photoId}` (RESTAURANT_OWNER)

---

## ReviewController

### GET
- `/restaurants/{id}/reviews` (PUBLIC)

### POST
- `/restaurants/{id}/reviews/{reviewId}/reply` (RESTAURANT_OWNER)

---

## AdminRestaurantController

**ROLE: ADMIN for all**

### GET
- `/admin/restaurants`
- `/admin/restaurants/{id}`

### POST
- `/admin/restaurants/reindex`

### PUT
- `/admin/restaurants/{id}/status`
- `/admin/restaurants/{id}/commission`

### DELETE
- `/admin/restaurants/{id}/reviews/{reviewId}/flag`
