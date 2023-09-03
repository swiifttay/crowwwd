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

eval(__webpack_require__.ts("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   \"default\": function() { return /* binding */ RegisterForm; }\n/* harmony export */ });\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/jsx-dev-runtime.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/index.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var _components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../components/Login/DataEntry */ \"(app-pages-browser)/./src/app/components/Login/DataEntry.tsx\");\n/* harmony import */ var _axios_apiService__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../axios/apiService */ \"(app-pages-browser)/./src/app/axios/apiService.tsx\");\n/* __next_internal_client_entry_do_not_use__ default auto */ \nvar _s = $RefreshSig$();\n\n\n\nfunction RegisterForm() {\n    _s();\n    const [firstName, setEnteredFirstName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [enteredLastName, setEnteredLastName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [enteredUsername, setEnteredUsername] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [enteredPassword, setEnteredPassword] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const submitHandler = async (e)=>{\n        e.preventDefault();\n        const formData = {\n            firstName: firstName,\n            lastName: enteredLastName,\n            username: enteredUsername,\n            password: enteredPassword\n        };\n        console.log(formData);\n        try {\n            const response = await (0,_axios_apiService__WEBPACK_IMPORTED_MODULE_3__.register)(firstName, enteredLastName, enteredUsername, enteredPassword);\n        } catch (error) {\n            console.log(error);\n            console.log(\"error caught\");\n        }\n    };\n    const updateTextHandler = (enteredText, id)=>{\n        if (id == \"firstName\") {\n            setEnteredFirstName(enteredText);\n        } else if (id == \"lastName\") {\n            setEnteredLastName(enteredText);\n        } else if (id == \"username\") {\n            setEnteredUsername(enteredText);\n        } else if (id == \"password\") {\n            setEnteredPassword(enteredText);\n        }\n    };\n    return /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"form\", {\n        className: \"mt-8 w-full max-w-sm\",\n        onSubmit: submitHandler,\n        children: [\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n                className: \"flex space-x-2\",\n                children: [\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"firstName\",\n                        placeholder: \"First Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 49,\n                        columnNumber: 9\n                    }, this),\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"lastName\",\n                        placeholder: \"Last Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 55,\n                        columnNumber: 9\n                    }, this)\n                ]\n            }, void 0, true, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 48,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"text\",\n                id: \"username\",\n                placeholder: \"Username/email\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 62,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"password\",\n                id: \"password\",\n                placeholder: \"Password\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 68,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"button\", {\n                type: \"submit\",\n                className: \"mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue\",\n                children: \"Sign Up\"\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 75,\n                columnNumber: 7\n            }, this)\n        ]\n    }, void 0, true, {\n        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n        lineNumber: 47,\n        columnNumber: 5\n    }, this);\n}\n_s(RegisterForm, \"Ywv8DWT2wcjRu/8Nt/utDx0Ikxs=\");\n_c = RegisterForm;\nvar _c;\n$RefreshReg$(_c, \"RegisterForm\");\n\n\n;\n    // Wrapped in an IIFE to avoid polluting the global scope\n    ;\n    (function () {\n        var _a, _b;\n        // Legacy CSS implementations will `eval` browser code in a Node.js context\n        // to extract CSS. For backwards compatibility, we need to check we're in a\n        // browser context before continuing.\n        if (typeof self !== 'undefined' &&\n            // AMP / No-JS mode does not inject these helpers:\n            '$RefreshHelpers$' in self) {\n            // @ts-ignore __webpack_module__ is global\n            var currentExports = module.exports;\n            // @ts-ignore __webpack_module__ is global\n            var prevExports = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevExports) !== null && _b !== void 0 ? _b : null;\n            // This cannot happen in MainTemplate because the exports mismatch between\n            // templating and execution.\n            self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);\n            // A module can be accepted automatically based on its exports, e.g. when\n            // it is a Refresh Boundary.\n            if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {\n                // Save the previous exports on update so we can compare the boundary\n                // signatures.\n                module.hot.dispose(function (data) {\n                    data.prevExports = currentExports;\n                });\n                // Unconditionally accept an update to this module, we'll check if it's\n                // still a Refresh Boundary later.\n                // @ts-ignore importMeta is replaced in the loader\n                module.hot.accept();\n                // This field is set when the previous version of this module was a\n                // Refresh Boundary, letting us know we need to check for invalidation or\n                // enqueue an update.\n                if (prevExports !== null) {\n                    // A boundary can become ineligible if its exports are incompatible\n                    // with the previous exports.\n                    //\n                    // For example, if you add/remove/change exports, we'll want to\n                    // re-execute the importing modules, and force those components to\n                    // re-render. Similarly, if you convert a class component to a\n                    // function, we want to invalidate the boundary.\n                    if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevExports, currentExports)) {\n                        module.hot.invalidate();\n                    }\n                    else {\n                        self.$RefreshHelpers$.scheduleUpdate();\n                    }\n                }\n            }\n            else {\n                // Since we just executed the code for the module, it's possible that the\n                // new exports made it ineligible for being a boundary.\n                // We only care about the case when we were _previously_ a boundary,\n                // because we already accepted this update (accidental side effect).\n                var isNoLongerABoundary = prevExports !== null;\n                if (isNoLongerABoundary) {\n                    module.hot.invalidate();\n                }\n            }\n        }\n    })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiKGFwcC1wYWdlcy1icm93c2VyKS8uL3NyYy9hcHAvcmVnaXN0ZXIvUmVnaXN0ZXJGb3JtLnRzeCIsIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7OztBQUV3QztBQUNjO0FBQ1Q7QUFFOUIsU0FBU0k7O0lBQ3RCLE1BQU0sQ0FBQ0MsV0FBV0Msb0JBQW9CLEdBQUdMLCtDQUFRQSxDQUFDO0lBQ2xELE1BQU0sQ0FBQ00saUJBQWlCQyxtQkFBbUIsR0FBR1AsK0NBQVFBLENBQUM7SUFDdkQsTUFBTSxDQUFDUSxpQkFBaUJDLG1CQUFtQixHQUFHVCwrQ0FBUUEsQ0FBQztJQUN2RCxNQUFNLENBQUNVLGlCQUFpQkMsbUJBQW1CLEdBQUdYLCtDQUFRQSxDQUFDO0lBRXZELE1BQU1ZLGdCQUFnQixPQUFPQztRQUMzQkEsRUFBRUMsY0FBYztRQUVoQixNQUFNQyxXQUFXO1lBQ2ZYLFdBQVdBO1lBQ1hZLFVBQVVWO1lBQ1ZXLFVBQVVUO1lBQ1ZVLFVBQVVSO1FBQ1o7UUFFQVMsUUFBUUMsR0FBRyxDQUFDTDtRQUVaLElBQUk7WUFDRixNQUFNTSxXQUFXLE1BQU1uQiwyREFBUUEsQ0FBQ0UsV0FBV0UsaUJBQWlCRSxpQkFBaUJFO1FBQy9FLEVBQUUsT0FBT1ksT0FBTTtZQUNiSCxRQUFRQyxHQUFHLENBQUNFO1lBQ1pILFFBQVFDLEdBQUcsQ0FBQztRQUNkO0lBRUY7SUFFQSxNQUFNRyxvQkFBb0IsQ0FBQ0MsYUFBcUJDO1FBQzlDLElBQUlBLE1BQU0sYUFBYTtZQUNyQnBCLG9CQUFvQm1CO1FBQ3RCLE9BQU8sSUFBSUMsTUFBTSxZQUFZO1lBQzNCbEIsbUJBQW1CaUI7UUFDckIsT0FBTyxJQUFJQyxNQUFNLFlBQVk7WUFDM0JoQixtQkFBbUJlO1FBQ3JCLE9BQU8sSUFBSUMsTUFBTSxZQUFZO1lBQzNCZCxtQkFBbUJhO1FBQ3JCO0lBQ0Y7SUFFQSxxQkFDRSw4REFBQ0U7UUFBS0MsV0FBVTtRQUF1QkMsVUFBVWhCOzswQkFDL0MsOERBQUNpQjtnQkFBSUYsV0FBVTs7a0NBQ2IsOERBQUMxQixtRUFBU0E7d0JBQ1I2QixNQUFLO3dCQUNMTCxJQUFHO3dCQUNITSxhQUFZO3dCQUNaQyxjQUFjVDs7Ozs7O2tDQUVoQiw4REFBQ3RCLG1FQUFTQTt3QkFDUjZCLE1BQUs7d0JBQ0xMLElBQUc7d0JBQ0hNLGFBQVk7d0JBQ1pDLGNBQWNUOzs7Ozs7Ozs7Ozs7MEJBR2xCLDhEQUFDdEIsbUVBQVNBO2dCQUNSNkIsTUFBSztnQkFDTEwsSUFBRztnQkFDSE0sYUFBWTtnQkFDWkMsY0FBY1Q7Ozs7OzswQkFFaEIsOERBQUN0QixtRUFBU0E7Z0JBQ1I2QixNQUFLO2dCQUNMTCxJQUFHO2dCQUNITSxhQUFZO2dCQUNaQyxjQUFjVDs7Ozs7OzBCQUdoQiw4REFBQ1U7Z0JBQ0NILE1BQUs7Z0JBQ0xILFdBQVU7MEJBQ1g7Ozs7Ozs7Ozs7OztBQUtQO0dBNUV3QnhCO0tBQUFBIiwic291cmNlcyI6WyJ3ZWJwYWNrOi8vX05fRS8uL3NyYy9hcHAvcmVnaXN0ZXIvUmVnaXN0ZXJGb3JtLnRzeD9kZTAxIl0sInNvdXJjZXNDb250ZW50IjpbIlwidXNlIGNsaWVudFwiXHJcblxyXG5pbXBvcnQgUmVhY3QsIHsgdXNlU3RhdGUgfSBmcm9tIFwicmVhY3RcIjtcclxuaW1wb3J0IERhdGFFbnRyeSBmcm9tIFwiLi4vY29tcG9uZW50cy9Mb2dpbi9EYXRhRW50cnlcIjtcclxuaW1wb3J0IHtyZWdpc3Rlcn0gZnJvbSBcIi4uL2F4aW9zL2FwaVNlcnZpY2VcIjtcclxuXHJcbmV4cG9ydCBkZWZhdWx0IGZ1bmN0aW9uIFJlZ2lzdGVyRm9ybSgpIHtcclxuICBjb25zdCBbZmlyc3ROYW1lLCBzZXRFbnRlcmVkRmlyc3ROYW1lXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG4gIGNvbnN0IFtlbnRlcmVkTGFzdE5hbWUsIHNldEVudGVyZWRMYXN0TmFtZV0gPSB1c2VTdGF0ZShcIlwiKTtcclxuICBjb25zdCBbZW50ZXJlZFVzZXJuYW1lLCBzZXRFbnRlcmVkVXNlcm5hbWVdID0gdXNlU3RhdGUoXCJcIik7XHJcbiAgY29uc3QgW2VudGVyZWRQYXNzd29yZCwgc2V0RW50ZXJlZFBhc3N3b3JkXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG5cclxuICBjb25zdCBzdWJtaXRIYW5kbGVyID0gYXN5bmMgKGU6IGFueSkgPT4ge1xyXG4gICAgZS5wcmV2ZW50RGVmYXVsdCgpO1xyXG5cclxuICAgIGNvbnN0IGZvcm1EYXRhID0ge1xyXG4gICAgICBmaXJzdE5hbWU6IGZpcnN0TmFtZSxcclxuICAgICAgbGFzdE5hbWU6IGVudGVyZWRMYXN0TmFtZSxcclxuICAgICAgdXNlcm5hbWU6IGVudGVyZWRVc2VybmFtZSxcclxuICAgICAgcGFzc3dvcmQ6IGVudGVyZWRQYXNzd29yZCxcclxuICAgIH07XHJcblxyXG4gICAgY29uc29sZS5sb2coZm9ybURhdGEpO1xyXG5cclxuICAgIHRyeSB7XHJcbiAgICAgIGNvbnN0IHJlc3BvbnNlID0gYXdhaXQgcmVnaXN0ZXIoZmlyc3ROYW1lLCBlbnRlcmVkTGFzdE5hbWUsIGVudGVyZWRVc2VybmFtZSwgZW50ZXJlZFBhc3N3b3JkKTtcclxuICAgIH0gY2F0Y2ggKGVycm9yKXtcclxuICAgICAgY29uc29sZS5sb2coZXJyb3IpO1xyXG4gICAgICBjb25zb2xlLmxvZyhcImVycm9yIGNhdWdodFwiKTtcclxuICAgIH1cclxuICAgIFxyXG4gIH07XHJcblxyXG4gIGNvbnN0IHVwZGF0ZVRleHRIYW5kbGVyID0gKGVudGVyZWRUZXh0OiBzdHJpbmcsIGlkOiBzdHJpbmcpID0+IHtcclxuICAgIGlmIChpZCA9PSBcImZpcnN0TmFtZVwiKSB7XHJcbiAgICAgIHNldEVudGVyZWRGaXJzdE5hbWUoZW50ZXJlZFRleHQpO1xyXG4gICAgfSBlbHNlIGlmIChpZCA9PSBcImxhc3ROYW1lXCIpIHtcclxuICAgICAgc2V0RW50ZXJlZExhc3ROYW1lKGVudGVyZWRUZXh0KTtcclxuICAgIH0gZWxzZSBpZiAoaWQgPT0gXCJ1c2VybmFtZVwiKSB7XHJcbiAgICAgIHNldEVudGVyZWRVc2VybmFtZShlbnRlcmVkVGV4dCk7XHJcbiAgICB9IGVsc2UgaWYgKGlkID09IFwicGFzc3dvcmRcIikge1xyXG4gICAgICBzZXRFbnRlcmVkUGFzc3dvcmQoZW50ZXJlZFRleHQpO1xyXG4gICAgfVxyXG4gIH07XHJcblxyXG4gIHJldHVybiAoXHJcbiAgICA8Zm9ybSBjbGFzc05hbWU9XCJtdC04IHctZnVsbCBtYXgtdy1zbVwiIG9uU3VibWl0PXtzdWJtaXRIYW5kbGVyfT5cclxuICAgICAgPGRpdiBjbGFzc05hbWU9XCJmbGV4IHNwYWNlLXgtMlwiPlxyXG4gICAgICAgIDxEYXRhRW50cnlcclxuICAgICAgICAgIHR5cGU9XCJ0ZXh0XCJcclxuICAgICAgICAgIGlkPVwiZmlyc3ROYW1lXCJcclxuICAgICAgICAgIHBsYWNlaG9sZGVyPVwiRmlyc3QgTmFtZVwiXHJcbiAgICAgICAgICBvblRleHRDaGFuZ2U9e3VwZGF0ZVRleHRIYW5kbGVyfVxyXG4gICAgICAgIC8+XHJcbiAgICAgICAgPERhdGFFbnRyeVxyXG4gICAgICAgICAgdHlwZT1cInRleHRcIlxyXG4gICAgICAgICAgaWQ9XCJsYXN0TmFtZVwiXHJcbiAgICAgICAgICBwbGFjZWhvbGRlcj1cIkxhc3QgTmFtZVwiXHJcbiAgICAgICAgICBvblRleHRDaGFuZ2U9e3VwZGF0ZVRleHRIYW5kbGVyfVxyXG4gICAgICAgIC8+XHJcbiAgICAgIDwvZGl2PlxyXG4gICAgICA8RGF0YUVudHJ5XHJcbiAgICAgICAgdHlwZT1cInRleHRcIlxyXG4gICAgICAgIGlkPVwidXNlcm5hbWVcIlxyXG4gICAgICAgIHBsYWNlaG9sZGVyPVwiVXNlcm5hbWUvZW1haWxcIlxyXG4gICAgICAgIG9uVGV4dENoYW5nZT17dXBkYXRlVGV4dEhhbmRsZXJ9XHJcbiAgICAgIC8+XHJcbiAgICAgIDxEYXRhRW50cnlcclxuICAgICAgICB0eXBlPVwicGFzc3dvcmRcIlxyXG4gICAgICAgIGlkPVwicGFzc3dvcmRcIlxyXG4gICAgICAgIHBsYWNlaG9sZGVyPVwiUGFzc3dvcmRcIlxyXG4gICAgICAgIG9uVGV4dENoYW5nZT17dXBkYXRlVGV4dEhhbmRsZXJ9XHJcbiAgICAgIC8+XHJcblxyXG4gICAgICA8YnV0dG9uXHJcbiAgICAgICAgdHlwZT1cInN1Ym1pdFwiXHJcbiAgICAgICAgY2xhc3NOYW1lPVwibXQtNiB3LWZ1bGwgYmctdGhlbWUtYmx1ZSB0ZXh0LXdoaXRlIHB5LTIgcm91bmRlZC1sZyBob3ZlcjpiZy10aGVtZS1saWdodC1ibHVlXCJcclxuICAgICAgPlxyXG4gICAgICAgIFNpZ24gVXBcclxuICAgICAgPC9idXR0b24+XHJcbiAgICA8L2Zvcm0+XHJcbiAgKTtcclxufVxyXG4iXSwibmFtZXMiOlsiUmVhY3QiLCJ1c2VTdGF0ZSIsIkRhdGFFbnRyeSIsInJlZ2lzdGVyIiwiUmVnaXN0ZXJGb3JtIiwiZmlyc3ROYW1lIiwic2V0RW50ZXJlZEZpcnN0TmFtZSIsImVudGVyZWRMYXN0TmFtZSIsInNldEVudGVyZWRMYXN0TmFtZSIsImVudGVyZWRVc2VybmFtZSIsInNldEVudGVyZWRVc2VybmFtZSIsImVudGVyZWRQYXNzd29yZCIsInNldEVudGVyZWRQYXNzd29yZCIsInN1Ym1pdEhhbmRsZXIiLCJlIiwicHJldmVudERlZmF1bHQiLCJmb3JtRGF0YSIsImxhc3ROYW1lIiwidXNlcm5hbWUiLCJwYXNzd29yZCIsImNvbnNvbGUiLCJsb2ciLCJyZXNwb25zZSIsImVycm9yIiwidXBkYXRlVGV4dEhhbmRsZXIiLCJlbnRlcmVkVGV4dCIsImlkIiwiZm9ybSIsImNsYXNzTmFtZSIsIm9uU3VibWl0IiwiZGl2IiwidHlwZSIsInBsYWNlaG9sZGVyIiwib25UZXh0Q2hhbmdlIiwiYnV0dG9uIl0sInNvdXJjZVJvb3QiOiIifQ==\n//# sourceURL=webpack-internal:///(app-pages-browser)/./src/app/register/RegisterForm.tsx\n"));

/***/ })

});