"use strict";
/*
 * ATTENTION: An "eval-source-map" devtool has been used.
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file with attached SourceMaps in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
self["webpackHotUpdate_N_E"]("app/register/page",{

/***/ "(app-pages-browser)/./src/app/axios/apiService.tsx":
/*!**************************************!*\
  !*** ./src/app/axios/apiService.tsx ***!
  \**************************************/
/***/ (function(module, __webpack_exports__, __webpack_require__) {

eval(__webpack_require__.ts("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   login: function() { return /* binding */ login; },\n/* harmony export */   register: function() { return /* binding */ register; }\n/* harmony export */ });\n/* harmony import */ var axios__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! axios */ \"(app-pages-browser)/./node_modules/axios/lib/axios.js\");\n\nconst api = axios__WEBPACK_IMPORTED_MODULE_0__[\"default\"].create({\n    //TODO: backend to provide\n    baseURL: \"http://localhost:8080/api/\"\n});\nconst login = async (username, password)=>{\n    try {\n        const response = await api.post(\"/login\", {\n            username,\n            password\n        });\n        return response.data;\n    } catch (error) {\n        // error will be handled in LoginForm.tsx\n        throw error;\n    }\n};\nconst register = async (firstName, lastName, username, password)=>{\n    try {\n        const response = await api.post(\"/register\", {\n            firstName,\n            lastName,\n            username,\n            password\n        });\n        return response.data;\n    } catch (error) {\n        // error will be handled in RegisterForm.tsx\n        throw error;\n    }\n};\n\n\n;\n    // Wrapped in an IIFE to avoid polluting the global scope\n    ;\n    (function () {\n        var _a, _b;\n        // Legacy CSS implementations will `eval` browser code in a Node.js context\n        // to extract CSS. For backwards compatibility, we need to check we're in a\n        // browser context before continuing.\n        if (typeof self !== 'undefined' &&\n            // AMP / No-JS mode does not inject these helpers:\n            '$RefreshHelpers$' in self) {\n            // @ts-ignore __webpack_module__ is global\n            var currentExports = module.exports;\n            // @ts-ignore __webpack_module__ is global\n            var prevExports = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevExports) !== null && _b !== void 0 ? _b : null;\n            // This cannot happen in MainTemplate because the exports mismatch between\n            // templating and execution.\n            self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);\n            // A module can be accepted automatically based on its exports, e.g. when\n            // it is a Refresh Boundary.\n            if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {\n                // Save the previous exports on update so we can compare the boundary\n                // signatures.\n                module.hot.dispose(function (data) {\n                    data.prevExports = currentExports;\n                });\n                // Unconditionally accept an update to this module, we'll check if it's\n                // still a Refresh Boundary later.\n                // @ts-ignore importMeta is replaced in the loader\n                module.hot.accept();\n                // This field is set when the previous version of this module was a\n                // Refresh Boundary, letting us know we need to check for invalidation or\n                // enqueue an update.\n                if (prevExports !== null) {\n                    // A boundary can become ineligible if its exports are incompatible\n                    // with the previous exports.\n                    //\n                    // For example, if you add/remove/change exports, we'll want to\n                    // re-execute the importing modules, and force those components to\n                    // re-render. Similarly, if you convert a class component to a\n                    // function, we want to invalidate the boundary.\n                    if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevExports, currentExports)) {\n                        module.hot.invalidate();\n                    }\n                    else {\n                        self.$RefreshHelpers$.scheduleUpdate();\n                    }\n                }\n            }\n            else {\n                // Since we just executed the code for the module, it's possible that the\n                // new exports made it ineligible for being a boundary.\n                // We only care about the case when we were _previously_ a boundary,\n                // because we already accepted this update (accidental side effect).\n                var isNoLongerABoundary = prevExports !== null;\n                if (isNoLongerABoundary) {\n                    module.hot.invalidate();\n                }\n            }\n        }\n    })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiKGFwcC1wYWdlcy1icm93c2VyKS8uL3NyYy9hcHAvYXhpb3MvYXBpU2VydmljZS50c3giLCJtYXBwaW5ncyI6Ijs7Ozs7O0FBQTBCO0FBRTFCLE1BQU1DLE1BQU1ELDZDQUFLQSxDQUFDRSxNQUFNLENBQUM7SUFDckIsMEJBQTBCO0lBQzFCQyxTQUFRO0FBQ1o7QUFFTyxNQUFNQyxRQUFRLE9BQU9DLFVBQWlCQztJQUN6QyxJQUFJO1FBQ0EsTUFBTUMsV0FBVyxNQUFNTixJQUFJTyxJQUFJLENBQUMsVUFBVTtZQUFDSDtZQUFVQztRQUFRO1FBQzdELE9BQU9DLFNBQVNFLElBQUk7SUFDeEIsRUFBRSxPQUFPQyxPQUFNO1FBQ1gseUNBQXlDO1FBQ3pDLE1BQU1BO0lBQ1Y7QUFDSixFQUFDO0FBRU0sTUFBTUMsV0FBVyxPQUFPQyxXQUFrQkMsVUFBa0JSLFVBQWlCQztJQUNoRixJQUFJO1FBQ0EsTUFBTUMsV0FBVyxNQUFNTixJQUFJTyxJQUFJLENBQUMsYUFBYTtZQUFDSTtZQUFXQztZQUFVUjtZQUFVQztRQUFRO1FBQ3JGLE9BQU9DLFNBQVNFLElBQUk7SUFDeEIsRUFBRSxPQUFPQyxPQUFNO1FBQ1gsNENBQTRDO1FBQzVDLE1BQU1BO0lBQ1Y7QUFDSixFQUFDIiwic291cmNlcyI6WyJ3ZWJwYWNrOi8vX05fRS8uL3NyYy9hcHAvYXhpb3MvYXBpU2VydmljZS50c3g/OTQyMCJdLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgYXhpb3MgZnJvbSAnYXhpb3MnO1xyXG5cclxuY29uc3QgYXBpID0gYXhpb3MuY3JlYXRlKHtcclxuICAgIC8vVE9ETzogYmFja2VuZCB0byBwcm92aWRlXHJcbiAgICBiYXNlVVJMOidodHRwOi8vbG9jYWxob3N0OjgwODAvYXBpLydcclxufSlcclxuXHJcbmV4cG9ydCBjb25zdCBsb2dpbiA9IGFzeW5jICh1c2VybmFtZTpzdHJpbmcsIHBhc3N3b3JkOnN0cmluZykgPT4ge1xyXG4gICAgdHJ5IHtcclxuICAgICAgICBjb25zdCByZXNwb25zZSA9IGF3YWl0IGFwaS5wb3N0KCcvbG9naW4nLCB7dXNlcm5hbWUsIHBhc3N3b3JkfSk7XHJcbiAgICAgICAgcmV0dXJuIHJlc3BvbnNlLmRhdGE7XHJcbiAgICB9IGNhdGNoIChlcnJvcil7XHJcbiAgICAgICAgLy8gZXJyb3Igd2lsbCBiZSBoYW5kbGVkIGluIExvZ2luRm9ybS50c3hcclxuICAgICAgICB0aHJvdyBlcnJvcjtcclxuICAgIH1cclxufVxyXG5cclxuZXhwb3J0IGNvbnN0IHJlZ2lzdGVyID0gYXN5bmMgKGZpcnN0TmFtZTpzdHJpbmcsIGxhc3ROYW1lOiBzdHJpbmcsIHVzZXJuYW1lOnN0cmluZywgcGFzc3dvcmQ6c3RyaW5nKSA9PiB7XHJcbiAgICB0cnkge1xyXG4gICAgICAgIGNvbnN0IHJlc3BvbnNlID0gYXdhaXQgYXBpLnBvc3QoJy9yZWdpc3RlcicsIHtmaXJzdE5hbWUsIGxhc3ROYW1lLCB1c2VybmFtZSwgcGFzc3dvcmR9KTtcclxuICAgICAgICByZXR1cm4gcmVzcG9uc2UuZGF0YTtcclxuICAgIH0gY2F0Y2ggKGVycm9yKXtcclxuICAgICAgICAvLyBlcnJvciB3aWxsIGJlIGhhbmRsZWQgaW4gUmVnaXN0ZXJGb3JtLnRzeFxyXG4gICAgICAgIHRocm93IGVycm9yO1xyXG4gICAgfVxyXG59Il0sIm5hbWVzIjpbImF4aW9zIiwiYXBpIiwiY3JlYXRlIiwiYmFzZVVSTCIsImxvZ2luIiwidXNlcm5hbWUiLCJwYXNzd29yZCIsInJlc3BvbnNlIiwicG9zdCIsImRhdGEiLCJlcnJvciIsInJlZ2lzdGVyIiwiZmlyc3ROYW1lIiwibGFzdE5hbWUiXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///(app-pages-browser)/./src/app/axios/apiService.tsx\n"));

/***/ })

});