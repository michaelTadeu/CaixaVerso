#!/usr/bin/env bash
set -euo pipefail

echo "== Health =="
curl -s http://localhost:8080/health
echo
echo "== Instance =="
curl -s http://localhost:8080/instance
echo
echo "== Customer Totals (non-cached) =="
curl -s http://localhost:8080/analytics/customer-totals
echo
echo "== Merchant Totals (cached for ~5s) =="
echo "First call:"
curl -s http://localhost:8080/analytics/merchant-totals
echo
echo "Second call (should be cached):"
curl -s http://localhost:8080/analytics/merchant-totals
echo
