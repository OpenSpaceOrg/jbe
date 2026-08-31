# JBE
[![Build Status](https://travis-ci.org/OpenSpaceOrg/jbe.svg?branch=master)](https://travis-ci.org/OpenSpaceOrg/jbe)[![codecov](https://codecov.io/gh/OpenSpaceOrg/jbe/branch/master/graph/badge.svg)](https://codecov.io/gh/OpenSpaceOrg/jbe)

Java back-end for OpenSpace App

## responsibilities

* read from google sheet
* parse data
* use konopas json format as output

## links

* back-end live: -/- (we only deploy during the event)
* back-end PP: -/-
* front-end repository: [konopas](https://github.com/OpenSpaceOrg/konopas)
* front-end live: [scrts.de](https://scrts.de)
* front-end PP: -/-

# Deploy on Heroku

* `heroku apps:create openspaceapp-socrates2017 --region eu --remote socrates2017`
* `heroku config:set SHEET_ID=1ZeqR0jqFfSxaVTNEQ... -a openspaceapp-socrates2017`
* `heroku config:set API_KEY=y4bvwt4gnosb... -a openspaceapp-socrates2017`
