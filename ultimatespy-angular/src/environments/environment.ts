// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  serviceUrl: 'http://127.0.0.1:6001',
  paypal: {
    clientId: 'AYKZwJ4qq1Il0kwhIMwAyL-aA9pJpyHJT9TIZV_eSQnxVaaqIy_e5NVBd_zffeRVm2HjzizfUTYDXAdC',
    plan: {
      P9M1D0: 'P-00E70376FW352540XMBGX67Y',
      P9M3D10: 'P-0CE580322U8083337MBGYAAI',
      P9M6D20: 'P-9GF20225BD603374AMBGYBII',
      P9M12D30: 'P-42W71416VX170441LMBGYCFQ',
      P49M1D0: 'P-789870659U503350YMBGYC2I',
      P49M3D10: 'P-58268653TF0880424MBGYDSY',
      P49M6D20: 'P-0R664833XR642694UMBGYELA',
      P49M12D30: 'P-5X863537AC585081GMBGYFLQ',

      // Testing
      P1M1D0: 'P-1JM74009MX4645314MBGYIDY',
      P1M3D0: 'P-5HM36624H2278603SMBG77QY'
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
