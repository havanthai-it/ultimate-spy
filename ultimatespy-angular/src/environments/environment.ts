// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  serviceUrl: 'http://127.0.0.1:6001',
  paypal: {
    clientId: 'AYKZwJ4qq1Il0kwhIMwAyL-aA9pJpyHJT9TIZV_eSQnxVaaqIy_e5NVBd_zffeRVm2HjzizfUTYDXAdC',
    plan: {
      P9M1D0: 'P-5FA835832T7582351MBFFREA',
      P9M3D10: 'P-7BS59596TA409632RMBFFUBQ',
      P9M6D20: 'P-5AU13527R41205050MBFFTBQ',
      P9M12D30: 'P-0K9743284B2854510MBFFU6Y',
      P49M1D0: 'P-2CV43316ND287443FMBFFVQQ',
      P49M3D10: 'P-8LS183125N633221HMBFFWBY',
      P49M6D20: 'P-9K175026CS215803FMBFFWTQ',
      P49M12D30: 'P-70T27222015227125MBFFXCI'
    }
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
