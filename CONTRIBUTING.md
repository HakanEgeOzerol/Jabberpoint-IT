# Development Workflow

1. Development
   - Create feature branches from `development`
   - Make changes and test locally
   - Create PR to merge into `development`

2. Testing
   - Create PR from `development` to `testing`
   - Integration tests run automatically
   - Review test results

3. Acceptance
   - Create PR from `testing` to `acceptance`
   - Acceptance tests run automatically
   - Stakeholder review

4. Production
   - Create PR from `acceptance` to `main`
   - Final review and deployment 