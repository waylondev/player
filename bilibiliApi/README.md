# Bilibili API Module

## Implemented APIs

### Home Module

#### Hot Ranking
- **Service**: `HotRankingService`
- **Request**: `HotRankingRequest`
- **Response**: `HotRankingResponse`
- **Endpoint**: `/x/web-interface/ranking/v2`

#### Homepage Recommendations
- **Service**: `HomeRecommendationService`
- **Request**: `HomeRecommendRequest`
- **Response**: `HomeRecommendResponse`
- **Endpoint**: `/x/web-interface/index/top/feed/rcmd`

### Video Module

#### Video Detail
- **Service**: `VideoDetailService`
- **Request**: `VideoDetailRequest`
- **Response**: `VideoDetailResponse`
- **Endpoint**: `/x/web-interface/view`

#### Video Stream
- **Service**: `VideoStreamService`
- **Request**: `VideoStreamRequest`
- **Response**: `VideoStreamResponse`
- **Endpoint**: `/x/player/playurl`

#### Related Video Recommendations  
- **Service**: `RelatedVideoService`
- **Request**: `RelatedVideoRequest`
- **Response**: `RelatedVideoResponse`
- **Endpoint**: `/x/web-interface/archive/related`

### Search Module

#### All Search
- **Service**: `AllSearchService`
- **Request**: `AllSearchRequest`
- **Response**: `AllSearchResponse`
- **Endpoint**: `/x/web-interface/search/all/v2`

### User Module

#### Nav Information
- **Service**: `NavService`
- **Request**: `NavRequest`
- **Response**: `NavResponse`
- **Endpoint**: `/x/web-interface/nav`

#### QR Code Login
- **Generate QR Code**: `QRCodeGenerateService`
  - **Request**: `QRCodeGenerateRequest`
  - **Response**: `QRCodeGenerateResponse`
  - **Endpoint**: `/x/passport-login/web/qrcode/generate`

- **Poll QR Code Status**: `QRCodePollService`
  - **Request**: `QRCodePollRequest`
  - **Response**: `QRCodePollResponse`
  - **Endpoint**: `/x/passport-login/web/qrcode/poll`

## Core Interface

```kotlin
interface ApiService<REQUEST> {
    suspend fun execute(request: REQUEST): HttpResponse
}
```

Follow this pattern to easily add new API services:

1. **Create Functional Module Directory**
   ```
   features/{domain}/{subdomain}/
   ```

2. **Define Request/Response Models**
   ```kotlin
   @Serializable
   data class {Module}Request(
       // Request parameters
   )
   
   @Serializable
   data class {Module}Response(
       // Response data
   )
   ```

3. **Implement Service Class**
   ```kotlin
   object {Module}Service : ApiService<{Module}Request> {
       override suspend fun execute(request: {Module}Request): HttpResponse {
           return ApiClient.client.get("https://api.bilibili.com/xxx") {
               // Request configuration
           }
       }
   }
   ```

4. **Add Tests**
   ```kotlin
   class {Module}ServiceTest {
       @Test
       fun test{Module}Service() = runTest {
           val request = {Module}Request()
           val response = {Module}Service.execute(request).body<JsonObject>()
           // Assertion verification
       }
   }
   ```

## Technical Features

### Type Safety
- All API responses use precise type definitions
- Nullable fields use `null` instead of default values to avoid ambiguity
- Full utilization of Kotlin's null safety features

### Error Handling
- Unified error code and message handling
- Exception propagation mechanism
- Network request retry strategy

### Testing Support
- Complete unit test coverage
- Mock network request testing
- Asynchronous test support

## Performance Optimization

### HTTP Client Optimization
- Singleton pattern avoids repeated creation
- Connection pool management
- Request/response logging

### Serialization Optimization
- Kotlinx Serialization high-performance serialization
- Minimal data transmission
- Support for dynamic JSON structures

## Extension Suggestions

### Planned Functional Modules
1. **User Module**
   - User information retrieval
   - User dynamic queries
   - Follow/follower management

2. **Video Module**
   - Video details retrieval
   - Video playback information
   - Danmaku retrieval

3. **Live Module**
   - Live room information
   - Live stream retrieval
   - Danmaku connection

4. **Dynamic Module**
   - Dynamic list
   - Dynamic details
   - Interaction operations

### Technical Improvement Directions
1. **Cache Strategy**: Add request result caching
2. **Authentication Management**: OAuth 2.0 authentication flow
3. **Monitoring Metrics**: API call monitoring and statistics
4. **Configuration Management**: Dynamic configuration support

## Contribution Guidelines

1. Follow existing code style and architecture patterns
2. Ensure all new features have corresponding tests
3. Use shared models to avoid duplicate definitions
4. Maintain API interface consistency

## License

This project uses the MIT License.

---

**Design Philosophy**: Simple is beautiful, performance is king, extensibility is fundamental