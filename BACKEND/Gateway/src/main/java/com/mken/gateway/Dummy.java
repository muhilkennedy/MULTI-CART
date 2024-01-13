package com.mken.gateway;

public class Dummy {
//
//public class LoggingFilter implements GlobalFilter {
//    Log log = LogFactory.getLog(getClass());
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
//        String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
//        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
//        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
//        log.info("Incoming request " + originalUri + " is routed to id: " + route.getId()
//                + ", uri:" + routeUri);
//        return chain.filter(exchange);
//    }
//}
}
