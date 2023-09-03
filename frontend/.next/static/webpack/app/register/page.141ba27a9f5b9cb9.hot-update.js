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

eval(__webpack_require__.ts("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   \"default\": function() { return /* binding */ RegisterForm; }\n/* harmony export */ });\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/jsx-dev-runtime.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/index.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var _components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../components/Login/DataEntry */ \"(app-pages-browser)/./src/app/components/Login/DataEntry.tsx\");\n/* harmony import */ var _axios_apiService__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../axios/apiService */ \"(app-pages-browser)/./src/app/axios/apiService.tsx\");\n/* __next_internal_client_entry_do_not_use__ default auto */ \nvar _s = $RefreshSig$();\n\n\n\nfunction RegisterForm() {\n    _s();\n    const [firstName, setFirstName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [lastName, setLastName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [username, setUsername] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [password, setPassword] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const submitHandler = async (e)=>{\n        e.preventDefault();\n        const formData = {\n            firstName,\n            lastName,\n            username,\n            password\n        };\n        //console.log(formData);\n        try {\n            const response = await (0,_axios_apiService__WEBPACK_IMPORTED_MODULE_3__.register)(firstName, lastName, username, password);\n        } catch (error) {\n            console.log(error.response);\n            console.log(\"error caught\");\n        }\n    };\n    const updateTextHandler = (enteredText, id)=>{\n        if (id == \"firstName\") {\n            setFirstName(enteredText);\n        } else if (id == \"lastName\") {\n            setLastName(enteredText);\n        } else if (id == \"username\") {\n            setUsername(enteredText);\n        } else if (id == \"password\") {\n            setPassword(enteredText);\n        }\n    };\n    return /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"form\", {\n        className: \"mt-8 w-full max-w-sm\",\n        onSubmit: submitHandler,\n        children: [\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n                className: \"flex space-x-2\",\n                children: [\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"firstName\",\n                        placeholder: \"First Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 47,\n                        columnNumber: 9\n                    }, this),\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"lastName\",\n                        placeholder: \"Last Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 53,\n                        columnNumber: 9\n                    }, this)\n                ]\n            }, void 0, true, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 46,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"text\",\n                id: \"username\",\n                placeholder: \"Username/email\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 60,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"password\",\n                id: \"password\",\n                placeholder: \"Password\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 66,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"button\", {\n                type: \"submit\",\n                className: \"mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue\",\n                children: \"Sign Up\"\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 73,\n                columnNumber: 7\n            }, this)\n        ]\n    }, void 0, true, {\n        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n        lineNumber: 45,\n        columnNumber: 5\n    }, this);\n}\n_s(RegisterForm, \"p4Vp/PCFzAvrASq7/uVTZB1dApM=\");\n_c = RegisterForm;\nvar _c;\n$RefreshReg$(_c, \"RegisterForm\");\n\n\n;\n    // Wrapped in an IIFE to avoid polluting the global scope\n    ;\n    (function () {\n        var _a, _b;\n        // Legacy CSS implementations will `eval` browser code in a Node.js context\n        // to extract CSS. For backwards compatibility, we need to check we're in a\n        // browser context before continuing.\n        if (typeof self !== 'undefined' &&\n            // AMP / No-JS mode does not inject these helpers:\n            '$RefreshHelpers$' in self) {\n            // @ts-ignore __webpack_module__ is global\n            var currentExports = module.exports;\n            // @ts-ignore __webpack_module__ is global\n            var prevExports = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevExports) !== null && _b !== void 0 ? _b : null;\n            // This cannot happen in MainTemplate because the exports mismatch between\n            // templating and execution.\n            self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);\n            // A module can be accepted automatically based on its exports, e.g. when\n            // it is a Refresh Boundary.\n            if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {\n                // Save the previous exports on update so we can compare the boundary\n                // signatures.\n                module.hot.dispose(function (data) {\n                    data.prevExports = currentExports;\n                });\n                // Unconditionally accept an update to this module, we'll check if it's\n                // still a Refresh Boundary later.\n                // @ts-ignore importMeta is replaced in the loader\n                module.hot.accept();\n                // This field is set when the previous version of this module was a\n                // Refresh Boundary, letting us know we need to check for invalidation or\n                // enqueue an update.\n                if (prevExports !== null) {\n                    // A boundary can become ineligible if its exports are incompatible\n                    // with the previous exports.\n                    //\n                    // For example, if you add/remove/change exports, we'll want to\n                    // re-execute the importing modules, and force those components to\n                    // re-render. Similarly, if you convert a class component to a\n                    // function, we want to invalidate the boundary.\n                    if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevExports, currentExports)) {\n                        module.hot.invalidate();\n                    }\n                    else {\n                        self.$RefreshHelpers$.scheduleUpdate();\n                    }\n                }\n            }\n            else {\n                // Since we just executed the code for the module, it's possible that the\n                // new exports made it ineligible for being a boundary.\n                // We only care about the case when we were _previously_ a boundary,\n                // because we already accepted this update (accidental side effect).\n                var isNoLongerABoundary = prevExports !== null;\n                if (isNoLongerABoundary) {\n                    module.hot.invalidate();\n                }\n            }\n        }\n    })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiKGFwcC1wYWdlcy1icm93c2VyKS8uL3NyYy9hcHAvcmVnaXN0ZXIvUmVnaXN0ZXJGb3JtLnRzeCIsIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7OztBQUV3QztBQUNjO0FBQ1A7QUFFaEMsU0FBU0k7O0lBQ3RCLE1BQU0sQ0FBQ0MsV0FBV0MsYUFBYSxHQUFHTCwrQ0FBUUEsQ0FBQztJQUMzQyxNQUFNLENBQUNNLFVBQVVDLFlBQVksR0FBR1AsK0NBQVFBLENBQUM7SUFDekMsTUFBTSxDQUFDUSxVQUFVQyxZQUFZLEdBQUdULCtDQUFRQSxDQUFDO0lBQ3pDLE1BQU0sQ0FBQ1UsVUFBVUMsWUFBWSxHQUFHWCwrQ0FBUUEsQ0FBQztJQUV6QyxNQUFNWSxnQkFBZ0IsT0FBT0M7UUFDM0JBLEVBQUVDLGNBQWM7UUFFaEIsTUFBTUMsV0FBVztZQUNmWDtZQUNBRTtZQUNBRTtZQUNBRTtRQUNGO1FBQ0Esd0JBQXdCO1FBRXhCLElBQUk7WUFDRixNQUFNTSxXQUFXLE1BQU1kLDJEQUFRQSxDQUFDRSxXQUFXRSxVQUFVRSxVQUFVRTtRQUNqRSxFQUFFLE9BQU9PLE9BQW1CO1lBQzFCQyxRQUFRQyxHQUFHLENBQUNGLE1BQU1ELFFBQVE7WUFDMUJFLFFBQVFDLEdBQUcsQ0FBQztRQUNkO0lBQ0Y7SUFFQSxNQUFNQyxvQkFBb0IsQ0FBQ0MsYUFBcUJDO1FBQzlDLElBQUlBLE1BQU0sYUFBYTtZQUNyQmpCLGFBQWFnQjtRQUNmLE9BQU8sSUFBSUMsTUFBTSxZQUFZO1lBQzNCZixZQUFZYztRQUNkLE9BQU8sSUFBSUMsTUFBTSxZQUFZO1lBQzNCYixZQUFZWTtRQUNkLE9BQU8sSUFBSUMsTUFBTSxZQUFZO1lBQzNCWCxZQUFZVTtRQUNkO0lBQ0Y7SUFFQSxxQkFDRSw4REFBQ0U7UUFBS0MsV0FBVTtRQUF1QkMsVUFBVWI7OzBCQUMvQyw4REFBQ2M7Z0JBQUlGLFdBQVU7O2tDQUNiLDhEQUFDdkIsbUVBQVNBO3dCQUNSMEIsTUFBSzt3QkFDTEwsSUFBRzt3QkFDSE0sYUFBWTt3QkFDWkMsY0FBY1Q7Ozs7OztrQ0FFaEIsOERBQUNuQixtRUFBU0E7d0JBQ1IwQixNQUFLO3dCQUNMTCxJQUFHO3dCQUNITSxhQUFZO3dCQUNaQyxjQUFjVDs7Ozs7Ozs7Ozs7OzBCQUdsQiw4REFBQ25CLG1FQUFTQTtnQkFDUjBCLE1BQUs7Z0JBQ0xMLElBQUc7Z0JBQ0hNLGFBQVk7Z0JBQ1pDLGNBQWNUOzs7Ozs7MEJBRWhCLDhEQUFDbkIsbUVBQVNBO2dCQUNSMEIsTUFBSztnQkFDTEwsSUFBRztnQkFDSE0sYUFBWTtnQkFDWkMsY0FBY1Q7Ozs7OzswQkFHaEIsOERBQUNVO2dCQUNDSCxNQUFLO2dCQUNMSCxXQUFVOzBCQUNYOzs7Ozs7Ozs7Ozs7QUFLUDtHQTFFd0JyQjtLQUFBQSIsInNvdXJjZXMiOlsid2VicGFjazovL19OX0UvLi9zcmMvYXBwL3JlZ2lzdGVyL1JlZ2lzdGVyRm9ybS50c3g/ZGUwMSJdLCJzb3VyY2VzQ29udGVudCI6WyJcInVzZSBjbGllbnRcIjtcclxuXHJcbmltcG9ydCBSZWFjdCwgeyB1c2VTdGF0ZSB9IGZyb20gXCJyZWFjdFwiO1xyXG5pbXBvcnQgRGF0YUVudHJ5IGZyb20gXCIuLi9jb21wb25lbnRzL0xvZ2luL0RhdGFFbnRyeVwiO1xyXG5pbXBvcnQgeyByZWdpc3RlciB9IGZyb20gXCIuLi9heGlvcy9hcGlTZXJ2aWNlXCI7XHJcblxyXG5leHBvcnQgZGVmYXVsdCBmdW5jdGlvbiBSZWdpc3RlckZvcm0oKSB7XHJcbiAgY29uc3QgW2ZpcnN0TmFtZSwgc2V0Rmlyc3ROYW1lXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG4gIGNvbnN0IFtsYXN0TmFtZSwgc2V0TGFzdE5hbWVdID0gdXNlU3RhdGUoXCJcIik7XHJcbiAgY29uc3QgW3VzZXJuYW1lLCBzZXRVc2VybmFtZV0gPSB1c2VTdGF0ZShcIlwiKTtcclxuICBjb25zdCBbcGFzc3dvcmQsIHNldFBhc3N3b3JkXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG5cclxuICBjb25zdCBzdWJtaXRIYW5kbGVyID0gYXN5bmMgKGU6IGFueSkgPT4ge1xyXG4gICAgZS5wcmV2ZW50RGVmYXVsdCgpO1xyXG5cclxuICAgIGNvbnN0IGZvcm1EYXRhID0ge1xyXG4gICAgICBmaXJzdE5hbWUsXHJcbiAgICAgIGxhc3ROYW1lLFxyXG4gICAgICB1c2VybmFtZSxcclxuICAgICAgcGFzc3dvcmQsXHJcbiAgICB9O1xyXG4gICAgLy9jb25zb2xlLmxvZyhmb3JtRGF0YSk7XHJcblxyXG4gICAgdHJ5IHtcclxuICAgICAgY29uc3QgcmVzcG9uc2UgPSBhd2FpdCByZWdpc3RlcihmaXJzdE5hbWUsIGxhc3ROYW1lLCB1c2VybmFtZSwgcGFzc3dvcmQpO1xyXG4gICAgfSBjYXRjaCAoZXJyb3I6IEF4aW9zRXJyb3IpIHtcclxuICAgICAgY29uc29sZS5sb2coZXJyb3IucmVzcG9uc2UpO1xyXG4gICAgICBjb25zb2xlLmxvZyhcImVycm9yIGNhdWdodFwiKTtcclxuICAgIH1cclxuICB9O1xyXG5cclxuICBjb25zdCB1cGRhdGVUZXh0SGFuZGxlciA9IChlbnRlcmVkVGV4dDogc3RyaW5nLCBpZDogc3RyaW5nKSA9PiB7XHJcbiAgICBpZiAoaWQgPT0gXCJmaXJzdE5hbWVcIikge1xyXG4gICAgICBzZXRGaXJzdE5hbWUoZW50ZXJlZFRleHQpO1xyXG4gICAgfSBlbHNlIGlmIChpZCA9PSBcImxhc3ROYW1lXCIpIHtcclxuICAgICAgc2V0TGFzdE5hbWUoZW50ZXJlZFRleHQpO1xyXG4gICAgfSBlbHNlIGlmIChpZCA9PSBcInVzZXJuYW1lXCIpIHtcclxuICAgICAgc2V0VXNlcm5hbWUoZW50ZXJlZFRleHQpO1xyXG4gICAgfSBlbHNlIGlmIChpZCA9PSBcInBhc3N3b3JkXCIpIHtcclxuICAgICAgc2V0UGFzc3dvcmQoZW50ZXJlZFRleHQpO1xyXG4gICAgfVxyXG4gIH07XHJcblxyXG4gIHJldHVybiAoXHJcbiAgICA8Zm9ybSBjbGFzc05hbWU9XCJtdC04IHctZnVsbCBtYXgtdy1zbVwiIG9uU3VibWl0PXtzdWJtaXRIYW5kbGVyfT5cclxuICAgICAgPGRpdiBjbGFzc05hbWU9XCJmbGV4IHNwYWNlLXgtMlwiPlxyXG4gICAgICAgIDxEYXRhRW50cnlcclxuICAgICAgICAgIHR5cGU9XCJ0ZXh0XCJcclxuICAgICAgICAgIGlkPVwiZmlyc3ROYW1lXCJcclxuICAgICAgICAgIHBsYWNlaG9sZGVyPVwiRmlyc3QgTmFtZVwiXHJcbiAgICAgICAgICBvblRleHRDaGFuZ2U9e3VwZGF0ZVRleHRIYW5kbGVyfVxyXG4gICAgICAgIC8+XHJcbiAgICAgICAgPERhdGFFbnRyeVxyXG4gICAgICAgICAgdHlwZT1cInRleHRcIlxyXG4gICAgICAgICAgaWQ9XCJsYXN0TmFtZVwiXHJcbiAgICAgICAgICBwbGFjZWhvbGRlcj1cIkxhc3QgTmFtZVwiXHJcbiAgICAgICAgICBvblRleHRDaGFuZ2U9e3VwZGF0ZVRleHRIYW5kbGVyfVxyXG4gICAgICAgIC8+XHJcbiAgICAgIDwvZGl2PlxyXG4gICAgICA8RGF0YUVudHJ5XHJcbiAgICAgICAgdHlwZT1cInRleHRcIlxyXG4gICAgICAgIGlkPVwidXNlcm5hbWVcIlxyXG4gICAgICAgIHBsYWNlaG9sZGVyPVwiVXNlcm5hbWUvZW1haWxcIlxyXG4gICAgICAgIG9uVGV4dENoYW5nZT17dXBkYXRlVGV4dEhhbmRsZXJ9XHJcbiAgICAgIC8+XHJcbiAgICAgIDxEYXRhRW50cnlcclxuICAgICAgICB0eXBlPVwicGFzc3dvcmRcIlxyXG4gICAgICAgIGlkPVwicGFzc3dvcmRcIlxyXG4gICAgICAgIHBsYWNlaG9sZGVyPVwiUGFzc3dvcmRcIlxyXG4gICAgICAgIG9uVGV4dENoYW5nZT17dXBkYXRlVGV4dEhhbmRsZXJ9XHJcbiAgICAgIC8+XHJcblxyXG4gICAgICA8YnV0dG9uXHJcbiAgICAgICAgdHlwZT1cInN1Ym1pdFwiXHJcbiAgICAgICAgY2xhc3NOYW1lPVwibXQtNiB3LWZ1bGwgYmctdGhlbWUtYmx1ZSB0ZXh0LXdoaXRlIHB5LTIgcm91bmRlZC1sZyBob3ZlcjpiZy10aGVtZS1saWdodC1ibHVlXCJcclxuICAgICAgPlxyXG4gICAgICAgIFNpZ24gVXBcclxuICAgICAgPC9idXR0b24+XHJcbiAgICA8L2Zvcm0+XHJcbiAgKTtcclxufVxyXG4iXSwibmFtZXMiOlsiUmVhY3QiLCJ1c2VTdGF0ZSIsIkRhdGFFbnRyeSIsInJlZ2lzdGVyIiwiUmVnaXN0ZXJGb3JtIiwiZmlyc3ROYW1lIiwic2V0Rmlyc3ROYW1lIiwibGFzdE5hbWUiLCJzZXRMYXN0TmFtZSIsInVzZXJuYW1lIiwic2V0VXNlcm5hbWUiLCJwYXNzd29yZCIsInNldFBhc3N3b3JkIiwic3VibWl0SGFuZGxlciIsImUiLCJwcmV2ZW50RGVmYXVsdCIsImZvcm1EYXRhIiwicmVzcG9uc2UiLCJlcnJvciIsImNvbnNvbGUiLCJsb2ciLCJ1cGRhdGVUZXh0SGFuZGxlciIsImVudGVyZWRUZXh0IiwiaWQiLCJmb3JtIiwiY2xhc3NOYW1lIiwib25TdWJtaXQiLCJkaXYiLCJ0eXBlIiwicGxhY2Vob2xkZXIiLCJvblRleHRDaGFuZ2UiLCJidXR0b24iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///(app-pages-browser)/./src/app/register/RegisterForm.tsx\n"));

/***/ })

});