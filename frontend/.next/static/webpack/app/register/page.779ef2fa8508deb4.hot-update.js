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

/***/ "(app-pages-browser)/./src/app/register/RegisterForm.tsx":
/*!*******************************************!*\
  !*** ./src/app/register/RegisterForm.tsx ***!
  \*******************************************/
/***/ (function(module, __webpack_exports__, __webpack_require__) {

eval(__webpack_require__.ts("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   \"default\": function() { return /* binding */ RegisterForm; }\n/* harmony export */ });\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/jsx-dev-runtime.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/index.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var _components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../components/Login/DataEntry */ \"(app-pages-browser)/./src/app/components/Login/DataEntry.tsx\");\n/* harmony import */ var _axios_apiService__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../axios/apiService */ \"(app-pages-browser)/./src/app/axios/apiService.tsx\");\n/* __next_internal_client_entry_do_not_use__ default auto */ \nvar _s = $RefreshSig$();\n\n\n\nfunction RegisterForm() {\n    _s();\n    const [firstName, setFirstName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [lastName, setLastName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [username, setEnteredUsername] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [enteredPassword, setEnteredPassword] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const submitHandler = async (e)=>{\n        e.preventDefault();\n        const formData = {\n            firstName: firstName,\n            lastName: lastName,\n            username: username,\n            password: enteredPassword\n        };\n        console.log(formData);\n        try {\n            const response = await (0,_axios_apiService__WEBPACK_IMPORTED_MODULE_3__.register)(firstName, lastName, username, enteredPassword);\n        } catch (error) {\n            console.log(error);\n            console.log(\"error caught\");\n        }\n    };\n    const updateTextHandler = (enteredText, id)=>{\n        if (id == \"firstName\") {\n            setFirstName(enteredText);\n        } else if (id == \"lastName\") {\n            setLastName(enteredText);\n        } else if (id == \"username\") {\n            setEnteredUsername(enteredText);\n        } else if (id == \"password\") {\n            setEnteredPassword(enteredText);\n        }\n    };\n    return /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"form\", {\n        className: \"mt-8 w-full max-w-sm\",\n        onSubmit: submitHandler,\n        children: [\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n                className: \"flex space-x-2\",\n                children: [\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"firstName\",\n                        placeholder: \"First Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 49,\n                        columnNumber: 9\n                    }, this),\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"lastName\",\n                        placeholder: \"Last Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 55,\n                        columnNumber: 9\n                    }, this)\n                ]\n            }, void 0, true, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 48,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"text\",\n                id: \"username\",\n                placeholder: \"Username/email\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 62,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"password\",\n                id: \"password\",\n                placeholder: \"Password\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 68,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"button\", {\n                type: \"submit\",\n                className: \"mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue\",\n                children: \"Sign Up\"\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 75,\n                columnNumber: 7\n            }, this)\n        ]\n    }, void 0, true, {\n        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n        lineNumber: 47,\n        columnNumber: 5\n    }, this);\n}\n_s(RegisterForm, \"DQE40w5ohR3xNxGRnvFXPA/d2CA=\");\n_c = RegisterForm;\nvar _c;\n$RefreshReg$(_c, \"RegisterForm\");\n\n\n;\n    // Wrapped in an IIFE to avoid polluting the global scope\n    ;\n    (function () {\n        var _a, _b;\n        // Legacy CSS implementations will `eval` browser code in a Node.js context\n        // to extract CSS. For backwards compatibility, we need to check we're in a\n        // browser context before continuing.\n        if (typeof self !== 'undefined' &&\n            // AMP / No-JS mode does not inject these helpers:\n            '$RefreshHelpers$' in self) {\n            // @ts-ignore __webpack_module__ is global\n            var currentExports = module.exports;\n            // @ts-ignore __webpack_module__ is global\n            var prevExports = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevExports) !== null && _b !== void 0 ? _b : null;\n            // This cannot happen in MainTemplate because the exports mismatch between\n            // templating and execution.\n            self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);\n            // A module can be accepted automatically based on its exports, e.g. when\n            // it is a Refresh Boundary.\n            if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {\n                // Save the previous exports on update so we can compare the boundary\n                // signatures.\n                module.hot.dispose(function (data) {\n                    data.prevExports = currentExports;\n                });\n                // Unconditionally accept an update to this module, we'll check if it's\n                // still a Refresh Boundary later.\n                // @ts-ignore importMeta is replaced in the loader\n                module.hot.accept();\n                // This field is set when the previous version of this module was a\n                // Refresh Boundary, letting us know we need to check for invalidation or\n                // enqueue an update.\n                if (prevExports !== null) {\n                    // A boundary can become ineligible if its exports are incompatible\n                    // with the previous exports.\n                    //\n                    // For example, if you add/remove/change exports, we'll want to\n                    // re-execute the importing modules, and force those components to\n                    // re-render. Similarly, if you convert a class component to a\n                    // function, we want to invalidate the boundary.\n                    if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevExports, currentExports)) {\n                        module.hot.invalidate();\n                    }\n                    else {\n                        self.$RefreshHelpers$.scheduleUpdate();\n                    }\n                }\n            }\n            else {\n                // Since we just executed the code for the module, it's possible that the\n                // new exports made it ineligible for being a boundary.\n                // We only care about the case when we were _previously_ a boundary,\n                // because we already accepted this update (accidental side effect).\n                var isNoLongerABoundary = prevExports !== null;\n                if (isNoLongerABoundary) {\n                    module.hot.invalidate();\n                }\n            }\n        }\n    })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiKGFwcC1wYWdlcy1icm93c2VyKS8uL3NyYy9hcHAvcmVnaXN0ZXIvUmVnaXN0ZXJGb3JtLnRzeCIsIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7OztBQUV3QztBQUNjO0FBQ1Q7QUFFOUIsU0FBU0k7O0lBQ3RCLE1BQU0sQ0FBQ0MsV0FBV0MsYUFBYSxHQUFHTCwrQ0FBUUEsQ0FBQztJQUMzQyxNQUFNLENBQUNNLFVBQVVDLFlBQVksR0FBR1AsK0NBQVFBLENBQUM7SUFDekMsTUFBTSxDQUFDUSxVQUFVQyxtQkFBbUIsR0FBR1QsK0NBQVFBLENBQUM7SUFDaEQsTUFBTSxDQUFDVSxpQkFBaUJDLG1CQUFtQixHQUFHWCwrQ0FBUUEsQ0FBQztJQUV2RCxNQUFNWSxnQkFBZ0IsT0FBT0M7UUFDM0JBLEVBQUVDLGNBQWM7UUFFaEIsTUFBTUMsV0FBVztZQUNmWCxXQUFXQTtZQUNYRSxVQUFVQTtZQUNWRSxVQUFVQTtZQUNWUSxVQUFVTjtRQUNaO1FBRUFPLFFBQVFDLEdBQUcsQ0FBQ0g7UUFFWixJQUFJO1lBQ0YsTUFBTUksV0FBVyxNQUFNakIsMkRBQVFBLENBQUNFLFdBQVdFLFVBQVVFLFVBQVVFO1FBQ2pFLEVBQUUsT0FBT1UsT0FBTTtZQUNiSCxRQUFRQyxHQUFHLENBQUNFO1lBQ1pILFFBQVFDLEdBQUcsQ0FBQztRQUNkO0lBRUY7SUFFQSxNQUFNRyxvQkFBb0IsQ0FBQ0MsYUFBcUJDO1FBQzlDLElBQUlBLE1BQU0sYUFBYTtZQUNyQmxCLGFBQWFpQjtRQUNmLE9BQU8sSUFBSUMsTUFBTSxZQUFZO1lBQzNCaEIsWUFBWWU7UUFDZCxPQUFPLElBQUlDLE1BQU0sWUFBWTtZQUMzQmQsbUJBQW1CYTtRQUNyQixPQUFPLElBQUlDLE1BQU0sWUFBWTtZQUMzQlosbUJBQW1CVztRQUNyQjtJQUNGO0lBRUEscUJBQ0UsOERBQUNFO1FBQUtDLFdBQVU7UUFBdUJDLFVBQVVkOzswQkFDL0MsOERBQUNlO2dCQUFJRixXQUFVOztrQ0FDYiw4REFBQ3hCLG1FQUFTQTt3QkFDUjJCLE1BQUs7d0JBQ0xMLElBQUc7d0JBQ0hNLGFBQVk7d0JBQ1pDLGNBQWNUOzs7Ozs7a0NBRWhCLDhEQUFDcEIsbUVBQVNBO3dCQUNSMkIsTUFBSzt3QkFDTEwsSUFBRzt3QkFDSE0sYUFBWTt3QkFDWkMsY0FBY1Q7Ozs7Ozs7Ozs7OzswQkFHbEIsOERBQUNwQixtRUFBU0E7Z0JBQ1IyQixNQUFLO2dCQUNMTCxJQUFHO2dCQUNITSxhQUFZO2dCQUNaQyxjQUFjVDs7Ozs7OzBCQUVoQiw4REFBQ3BCLG1FQUFTQTtnQkFDUjJCLE1BQUs7Z0JBQ0xMLElBQUc7Z0JBQ0hNLGFBQVk7Z0JBQ1pDLGNBQWNUOzs7Ozs7MEJBR2hCLDhEQUFDVTtnQkFDQ0gsTUFBSztnQkFDTEgsV0FBVTswQkFDWDs7Ozs7Ozs7Ozs7O0FBS1A7R0E1RXdCdEI7S0FBQUEiLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly9fTl9FLy4vc3JjL2FwcC9yZWdpc3Rlci9SZWdpc3RlckZvcm0udHN4P2RlMDEiXSwic291cmNlc0NvbnRlbnQiOlsiXCJ1c2UgY2xpZW50XCJcclxuXHJcbmltcG9ydCBSZWFjdCwgeyB1c2VTdGF0ZSB9IGZyb20gXCJyZWFjdFwiO1xyXG5pbXBvcnQgRGF0YUVudHJ5IGZyb20gXCIuLi9jb21wb25lbnRzL0xvZ2luL0RhdGFFbnRyeVwiO1xyXG5pbXBvcnQge3JlZ2lzdGVyfSBmcm9tIFwiLi4vYXhpb3MvYXBpU2VydmljZVwiO1xyXG5cclxuZXhwb3J0IGRlZmF1bHQgZnVuY3Rpb24gUmVnaXN0ZXJGb3JtKCkge1xyXG4gIGNvbnN0IFtmaXJzdE5hbWUsIHNldEZpcnN0TmFtZV0gPSB1c2VTdGF0ZShcIlwiKTtcclxuICBjb25zdCBbbGFzdE5hbWUsIHNldExhc3ROYW1lXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG4gIGNvbnN0IFt1c2VybmFtZSwgc2V0RW50ZXJlZFVzZXJuYW1lXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG4gIGNvbnN0IFtlbnRlcmVkUGFzc3dvcmQsIHNldEVudGVyZWRQYXNzd29yZF0gPSB1c2VTdGF0ZShcIlwiKTtcclxuXHJcbiAgY29uc3Qgc3VibWl0SGFuZGxlciA9IGFzeW5jIChlOiBhbnkpID0+IHtcclxuICAgIGUucHJldmVudERlZmF1bHQoKTtcclxuXHJcbiAgICBjb25zdCBmb3JtRGF0YSA9IHtcclxuICAgICAgZmlyc3ROYW1lOiBmaXJzdE5hbWUsXHJcbiAgICAgIGxhc3ROYW1lOiBsYXN0TmFtZSxcclxuICAgICAgdXNlcm5hbWU6IHVzZXJuYW1lLFxyXG4gICAgICBwYXNzd29yZDogZW50ZXJlZFBhc3N3b3JkLFxyXG4gICAgfTtcclxuXHJcbiAgICBjb25zb2xlLmxvZyhmb3JtRGF0YSk7XHJcblxyXG4gICAgdHJ5IHtcclxuICAgICAgY29uc3QgcmVzcG9uc2UgPSBhd2FpdCByZWdpc3RlcihmaXJzdE5hbWUsIGxhc3ROYW1lLCB1c2VybmFtZSwgZW50ZXJlZFBhc3N3b3JkKTtcclxuICAgIH0gY2F0Y2ggKGVycm9yKXtcclxuICAgICAgY29uc29sZS5sb2coZXJyb3IpO1xyXG4gICAgICBjb25zb2xlLmxvZyhcImVycm9yIGNhdWdodFwiKTtcclxuICAgIH1cclxuICAgIFxyXG4gIH07XHJcblxyXG4gIGNvbnN0IHVwZGF0ZVRleHRIYW5kbGVyID0gKGVudGVyZWRUZXh0OiBzdHJpbmcsIGlkOiBzdHJpbmcpID0+IHtcclxuICAgIGlmIChpZCA9PSBcImZpcnN0TmFtZVwiKSB7XHJcbiAgICAgIHNldEZpcnN0TmFtZShlbnRlcmVkVGV4dCk7XHJcbiAgICB9IGVsc2UgaWYgKGlkID09IFwibGFzdE5hbWVcIikge1xyXG4gICAgICBzZXRMYXN0TmFtZShlbnRlcmVkVGV4dCk7XHJcbiAgICB9IGVsc2UgaWYgKGlkID09IFwidXNlcm5hbWVcIikge1xyXG4gICAgICBzZXRFbnRlcmVkVXNlcm5hbWUoZW50ZXJlZFRleHQpO1xyXG4gICAgfSBlbHNlIGlmIChpZCA9PSBcInBhc3N3b3JkXCIpIHtcclxuICAgICAgc2V0RW50ZXJlZFBhc3N3b3JkKGVudGVyZWRUZXh0KTtcclxuICAgIH1cclxuICB9O1xyXG5cclxuICByZXR1cm4gKFxyXG4gICAgPGZvcm0gY2xhc3NOYW1lPVwibXQtOCB3LWZ1bGwgbWF4LXctc21cIiBvblN1Ym1pdD17c3VibWl0SGFuZGxlcn0+XHJcbiAgICAgIDxkaXYgY2xhc3NOYW1lPVwiZmxleCBzcGFjZS14LTJcIj5cclxuICAgICAgICA8RGF0YUVudHJ5XHJcbiAgICAgICAgICB0eXBlPVwidGV4dFwiXHJcbiAgICAgICAgICBpZD1cImZpcnN0TmFtZVwiXHJcbiAgICAgICAgICBwbGFjZWhvbGRlcj1cIkZpcnN0IE5hbWVcIlxyXG4gICAgICAgICAgb25UZXh0Q2hhbmdlPXt1cGRhdGVUZXh0SGFuZGxlcn1cclxuICAgICAgICAvPlxyXG4gICAgICAgIDxEYXRhRW50cnlcclxuICAgICAgICAgIHR5cGU9XCJ0ZXh0XCJcclxuICAgICAgICAgIGlkPVwibGFzdE5hbWVcIlxyXG4gICAgICAgICAgcGxhY2Vob2xkZXI9XCJMYXN0IE5hbWVcIlxyXG4gICAgICAgICAgb25UZXh0Q2hhbmdlPXt1cGRhdGVUZXh0SGFuZGxlcn1cclxuICAgICAgICAvPlxyXG4gICAgICA8L2Rpdj5cclxuICAgICAgPERhdGFFbnRyeVxyXG4gICAgICAgIHR5cGU9XCJ0ZXh0XCJcclxuICAgICAgICBpZD1cInVzZXJuYW1lXCJcclxuICAgICAgICBwbGFjZWhvbGRlcj1cIlVzZXJuYW1lL2VtYWlsXCJcclxuICAgICAgICBvblRleHRDaGFuZ2U9e3VwZGF0ZVRleHRIYW5kbGVyfVxyXG4gICAgICAvPlxyXG4gICAgICA8RGF0YUVudHJ5XHJcbiAgICAgICAgdHlwZT1cInBhc3N3b3JkXCJcclxuICAgICAgICBpZD1cInBhc3N3b3JkXCJcclxuICAgICAgICBwbGFjZWhvbGRlcj1cIlBhc3N3b3JkXCJcclxuICAgICAgICBvblRleHRDaGFuZ2U9e3VwZGF0ZVRleHRIYW5kbGVyfVxyXG4gICAgICAvPlxyXG5cclxuICAgICAgPGJ1dHRvblxyXG4gICAgICAgIHR5cGU9XCJzdWJtaXRcIlxyXG4gICAgICAgIGNsYXNzTmFtZT1cIm10LTYgdy1mdWxsIGJnLXRoZW1lLWJsdWUgdGV4dC13aGl0ZSBweS0yIHJvdW5kZWQtbGcgaG92ZXI6YmctdGhlbWUtbGlnaHQtYmx1ZVwiXHJcbiAgICAgID5cclxuICAgICAgICBTaWduIFVwXHJcbiAgICAgIDwvYnV0dG9uPlxyXG4gICAgPC9mb3JtPlxyXG4gICk7XHJcbn1cclxuIl0sIm5hbWVzIjpbIlJlYWN0IiwidXNlU3RhdGUiLCJEYXRhRW50cnkiLCJyZWdpc3RlciIsIlJlZ2lzdGVyRm9ybSIsImZpcnN0TmFtZSIsInNldEZpcnN0TmFtZSIsImxhc3ROYW1lIiwic2V0TGFzdE5hbWUiLCJ1c2VybmFtZSIsInNldEVudGVyZWRVc2VybmFtZSIsImVudGVyZWRQYXNzd29yZCIsInNldEVudGVyZWRQYXNzd29yZCIsInN1Ym1pdEhhbmRsZXIiLCJlIiwicHJldmVudERlZmF1bHQiLCJmb3JtRGF0YSIsInBhc3N3b3JkIiwiY29uc29sZSIsImxvZyIsInJlc3BvbnNlIiwiZXJyb3IiLCJ1cGRhdGVUZXh0SGFuZGxlciIsImVudGVyZWRUZXh0IiwiaWQiLCJmb3JtIiwiY2xhc3NOYW1lIiwib25TdWJtaXQiLCJkaXYiLCJ0eXBlIiwicGxhY2Vob2xkZXIiLCJvblRleHRDaGFuZ2UiLCJidXR0b24iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///(app-pages-browser)/./src/app/register/RegisterForm.tsx\n"));

/***/ })

});