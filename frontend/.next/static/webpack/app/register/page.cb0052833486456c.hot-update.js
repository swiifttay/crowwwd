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

eval(__webpack_require__.ts("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   \"default\": function() { return /* binding */ RegisterForm; }\n/* harmony export */ });\n/* harmony import */ var react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react/jsx-dev-runtime */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/jsx-dev-runtime.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react */ \"(app-pages-browser)/./node_modules/next/dist/compiled/react/index.js\");\n/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_1__);\n/* harmony import */ var _components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../components/Login/DataEntry */ \"(app-pages-browser)/./src/app/components/Login/DataEntry.tsx\");\n/* harmony import */ var _axios_apiService__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../axios/apiService */ \"(app-pages-browser)/./src/app/axios/apiService.tsx\");\n/* __next_internal_client_entry_do_not_use__ default auto */ \nvar _s = $RefreshSig$();\n\n\n\nfunction RegisterForm() {\n    _s();\n    const [firstName, setFirstName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [lastName, setLastName] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [username, setUsername] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const [password, setPassword] = (0,react__WEBPACK_IMPORTED_MODULE_1__.useState)(\"\");\n    const submitHandler = async (e)=>{\n        e.preventDefault();\n        const formData = {\n            firstName,\n            lastNAme,\n            usernmae,\n            password\n        };\n        console.log(formData);\n        try {\n            const response = await (0,_axios_apiService__WEBPACK_IMPORTED_MODULE_3__.register)(firstName, lastName, username, password);\n        } catch (error) {\n            console.log(error);\n            console.log(\"error caught\");\n        }\n    };\n    const updateTextHandler = (enteredText, id)=>{\n        if (id == \"firstName\") {\n            setFirstName(enteredText);\n        } else if (id == \"lastName\") {\n            setLastName(enteredText);\n        } else if (id == \"username\") {\n            setUsername(enteredText);\n        } else if (id == \"password\") {\n            setPassword(enteredText);\n        }\n    };\n    return /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"form\", {\n        className: \"mt-8 w-full max-w-sm\",\n        onSubmit: submitHandler,\n        children: [\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"div\", {\n                className: \"flex space-x-2\",\n                children: [\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"firstName\",\n                        placeholder: \"First Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 46,\n                        columnNumber: 9\n                    }, this),\n                    /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                        type: \"text\",\n                        id: \"lastName\",\n                        placeholder: \"Last Name\",\n                        onTextChange: updateTextHandler\n                    }, void 0, false, {\n                        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                        lineNumber: 52,\n                        columnNumber: 9\n                    }, this)\n                ]\n            }, void 0, true, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 45,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"text\",\n                id: \"username\",\n                placeholder: \"Username/email\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 59,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(_components_Login_DataEntry__WEBPACK_IMPORTED_MODULE_2__[\"default\"], {\n                type: \"password\",\n                id: \"password\",\n                placeholder: \"Password\",\n                onTextChange: updateTextHandler\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 65,\n                columnNumber: 7\n            }, this),\n            /*#__PURE__*/ (0,react_jsx_dev_runtime__WEBPACK_IMPORTED_MODULE_0__.jsxDEV)(\"button\", {\n                type: \"submit\",\n                className: \"mt-6 w-full bg-theme-blue text-white py-2 rounded-lg hover:bg-theme-light-blue\",\n                children: \"Sign Up\"\n            }, void 0, false, {\n                fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n                lineNumber: 72,\n                columnNumber: 7\n            }, this)\n        ]\n    }, void 0, true, {\n        fileName: \"C:\\\\Users\\\\Joel\\\\Desktop\\\\school\\\\crowwwd\\\\frontend\\\\src\\\\app\\\\register\\\\RegisterForm.tsx\",\n        lineNumber: 44,\n        columnNumber: 5\n    }, this);\n}\n_s(RegisterForm, \"p4Vp/PCFzAvrASq7/uVTZB1dApM=\");\n_c = RegisterForm;\nvar _c;\n$RefreshReg$(_c, \"RegisterForm\");\n\n\n;\n    // Wrapped in an IIFE to avoid polluting the global scope\n    ;\n    (function () {\n        var _a, _b;\n        // Legacy CSS implementations will `eval` browser code in a Node.js context\n        // to extract CSS. For backwards compatibility, we need to check we're in a\n        // browser context before continuing.\n        if (typeof self !== 'undefined' &&\n            // AMP / No-JS mode does not inject these helpers:\n            '$RefreshHelpers$' in self) {\n            // @ts-ignore __webpack_module__ is global\n            var currentExports = module.exports;\n            // @ts-ignore __webpack_module__ is global\n            var prevExports = (_b = (_a = module.hot.data) === null || _a === void 0 ? void 0 : _a.prevExports) !== null && _b !== void 0 ? _b : null;\n            // This cannot happen in MainTemplate because the exports mismatch between\n            // templating and execution.\n            self.$RefreshHelpers$.registerExportsForReactRefresh(currentExports, module.id);\n            // A module can be accepted automatically based on its exports, e.g. when\n            // it is a Refresh Boundary.\n            if (self.$RefreshHelpers$.isReactRefreshBoundary(currentExports)) {\n                // Save the previous exports on update so we can compare the boundary\n                // signatures.\n                module.hot.dispose(function (data) {\n                    data.prevExports = currentExports;\n                });\n                // Unconditionally accept an update to this module, we'll check if it's\n                // still a Refresh Boundary later.\n                // @ts-ignore importMeta is replaced in the loader\n                module.hot.accept();\n                // This field is set when the previous version of this module was a\n                // Refresh Boundary, letting us know we need to check for invalidation or\n                // enqueue an update.\n                if (prevExports !== null) {\n                    // A boundary can become ineligible if its exports are incompatible\n                    // with the previous exports.\n                    //\n                    // For example, if you add/remove/change exports, we'll want to\n                    // re-execute the importing modules, and force those components to\n                    // re-render. Similarly, if you convert a class component to a\n                    // function, we want to invalidate the boundary.\n                    if (self.$RefreshHelpers$.shouldInvalidateReactRefreshBoundary(prevExports, currentExports)) {\n                        module.hot.invalidate();\n                    }\n                    else {\n                        self.$RefreshHelpers$.scheduleUpdate();\n                    }\n                }\n            }\n            else {\n                // Since we just executed the code for the module, it's possible that the\n                // new exports made it ineligible for being a boundary.\n                // We only care about the case when we were _previously_ a boundary,\n                // because we already accepted this update (accidental side effect).\n                var isNoLongerABoundary = prevExports !== null;\n                if (isNoLongerABoundary) {\n                    module.hot.invalidate();\n                }\n            }\n        }\n    })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiKGFwcC1wYWdlcy1icm93c2VyKS8uL3NyYy9hcHAvcmVnaXN0ZXIvUmVnaXN0ZXJGb3JtLnRzeCIsIm1hcHBpbmdzIjoiOzs7Ozs7Ozs7OztBQUV3QztBQUNjO0FBQ1Q7QUFFOUIsU0FBU0k7O0lBQ3RCLE1BQU0sQ0FBQ0MsV0FBV0MsYUFBYSxHQUFHTCwrQ0FBUUEsQ0FBQztJQUMzQyxNQUFNLENBQUNNLFVBQVVDLFlBQVksR0FBR1AsK0NBQVFBLENBQUM7SUFDekMsTUFBTSxDQUFDUSxVQUFVQyxZQUFZLEdBQUdULCtDQUFRQSxDQUFDO0lBQ3pDLE1BQU0sQ0FBQ1UsVUFBVUMsWUFBWSxHQUFHWCwrQ0FBUUEsQ0FBQztJQUV6QyxNQUFNWSxnQkFBZ0IsT0FBT0M7UUFDM0JBLEVBQUVDLGNBQWM7UUFFaEIsTUFBTUMsV0FBVztZQUNyQlg7WUFBV1k7WUFBV0M7WUFBVVA7UUFDNUI7UUFFQVEsUUFBUUMsR0FBRyxDQUFDSjtRQUVaLElBQUk7WUFDRixNQUFNSyxXQUFXLE1BQU1sQiwyREFBUUEsQ0FBQ0UsV0FBV0UsVUFBVUUsVUFBVUU7UUFDakUsRUFBRSxPQUFPVyxPQUFNO1lBQ2JILFFBQVFDLEdBQUcsQ0FBQ0U7WUFDWkgsUUFBUUMsR0FBRyxDQUFDO1FBQ2Q7SUFFRjtJQUVBLE1BQU1HLG9CQUFvQixDQUFDQyxhQUFxQkM7UUFDOUMsSUFBSUEsTUFBTSxhQUFhO1lBQ3JCbkIsYUFBYWtCO1FBQ2YsT0FBTyxJQUFJQyxNQUFNLFlBQVk7WUFDM0JqQixZQUFZZ0I7UUFDZCxPQUFPLElBQUlDLE1BQU0sWUFBWTtZQUMzQmYsWUFBWWM7UUFDZCxPQUFPLElBQUlDLE1BQU0sWUFBWTtZQUMzQmIsWUFBWVk7UUFDZDtJQUNGO0lBRUEscUJBQ0UsOERBQUNFO1FBQUtDLFdBQVU7UUFBdUJDLFVBQVVmOzswQkFDL0MsOERBQUNnQjtnQkFBSUYsV0FBVTs7a0NBQ2IsOERBQUN6QixtRUFBU0E7d0JBQ1I0QixNQUFLO3dCQUNMTCxJQUFHO3dCQUNITSxhQUFZO3dCQUNaQyxjQUFjVDs7Ozs7O2tDQUVoQiw4REFBQ3JCLG1FQUFTQTt3QkFDUjRCLE1BQUs7d0JBQ0xMLElBQUc7d0JBQ0hNLGFBQVk7d0JBQ1pDLGNBQWNUOzs7Ozs7Ozs7Ozs7MEJBR2xCLDhEQUFDckIsbUVBQVNBO2dCQUNSNEIsTUFBSztnQkFDTEwsSUFBRztnQkFDSE0sYUFBWTtnQkFDWkMsY0FBY1Q7Ozs7OzswQkFFaEIsOERBQUNyQixtRUFBU0E7Z0JBQ1I0QixNQUFLO2dCQUNMTCxJQUFHO2dCQUNITSxhQUFZO2dCQUNaQyxjQUFjVDs7Ozs7OzBCQUdoQiw4REFBQ1U7Z0JBQ0NILE1BQUs7Z0JBQ0xILFdBQVU7MEJBQ1g7Ozs7Ozs7Ozs7OztBQUtQO0dBekV3QnZCO0tBQUFBIiwic291cmNlcyI6WyJ3ZWJwYWNrOi8vX05fRS8uL3NyYy9hcHAvcmVnaXN0ZXIvUmVnaXN0ZXJGb3JtLnRzeD9kZTAxIl0sInNvdXJjZXNDb250ZW50IjpbIlwidXNlIGNsaWVudFwiXHJcblxyXG5pbXBvcnQgUmVhY3QsIHsgdXNlU3RhdGUgfSBmcm9tIFwicmVhY3RcIjtcclxuaW1wb3J0IERhdGFFbnRyeSBmcm9tIFwiLi4vY29tcG9uZW50cy9Mb2dpbi9EYXRhRW50cnlcIjtcclxuaW1wb3J0IHtyZWdpc3Rlcn0gZnJvbSBcIi4uL2F4aW9zL2FwaVNlcnZpY2VcIjtcclxuXHJcbmV4cG9ydCBkZWZhdWx0IGZ1bmN0aW9uIFJlZ2lzdGVyRm9ybSgpIHtcclxuICBjb25zdCBbZmlyc3ROYW1lLCBzZXRGaXJzdE5hbWVdID0gdXNlU3RhdGUoXCJcIik7XHJcbiAgY29uc3QgW2xhc3ROYW1lLCBzZXRMYXN0TmFtZV0gPSB1c2VTdGF0ZShcIlwiKTtcclxuICBjb25zdCBbdXNlcm5hbWUsIHNldFVzZXJuYW1lXSA9IHVzZVN0YXRlKFwiXCIpO1xyXG4gIGNvbnN0IFtwYXNzd29yZCwgc2V0UGFzc3dvcmRdID0gdXNlU3RhdGUoXCJcIik7XHJcblxyXG4gIGNvbnN0IHN1Ym1pdEhhbmRsZXIgPSBhc3luYyAoZTogYW55KSA9PiB7XHJcbiAgICBlLnByZXZlbnREZWZhdWx0KCk7XHJcblxyXG4gICAgY29uc3QgZm9ybURhdGEgPSB7XHJcbmZpcnN0TmFtZSwgbGFzdE5BbWUgLCB1c2Vybm1hZSwgcGFzc3dvcmRcclxuICAgIH07XHJcblxyXG4gICAgY29uc29sZS5sb2coZm9ybURhdGEpO1xyXG5cclxuICAgIHRyeSB7XHJcbiAgICAgIGNvbnN0IHJlc3BvbnNlID0gYXdhaXQgcmVnaXN0ZXIoZmlyc3ROYW1lLCBsYXN0TmFtZSwgdXNlcm5hbWUsIHBhc3N3b3JkKTtcclxuICAgIH0gY2F0Y2ggKGVycm9yKXtcclxuICAgICAgY29uc29sZS5sb2coZXJyb3IpO1xyXG4gICAgICBjb25zb2xlLmxvZyhcImVycm9yIGNhdWdodFwiKTtcclxuICAgIH1cclxuICAgIFxyXG4gIH07XHJcblxyXG4gIGNvbnN0IHVwZGF0ZVRleHRIYW5kbGVyID0gKGVudGVyZWRUZXh0OiBzdHJpbmcsIGlkOiBzdHJpbmcpID0+IHtcclxuICAgIGlmIChpZCA9PSBcImZpcnN0TmFtZVwiKSB7XHJcbiAgICAgIHNldEZpcnN0TmFtZShlbnRlcmVkVGV4dCk7XHJcbiAgICB9IGVsc2UgaWYgKGlkID09IFwibGFzdE5hbWVcIikge1xyXG4gICAgICBzZXRMYXN0TmFtZShlbnRlcmVkVGV4dCk7XHJcbiAgICB9IGVsc2UgaWYgKGlkID09IFwidXNlcm5hbWVcIikge1xyXG4gICAgICBzZXRVc2VybmFtZShlbnRlcmVkVGV4dCk7XHJcbiAgICB9IGVsc2UgaWYgKGlkID09IFwicGFzc3dvcmRcIikge1xyXG4gICAgICBzZXRQYXNzd29yZChlbnRlcmVkVGV4dCk7XHJcbiAgICB9XHJcbiAgfTtcclxuXHJcbiAgcmV0dXJuIChcclxuICAgIDxmb3JtIGNsYXNzTmFtZT1cIm10LTggdy1mdWxsIG1heC13LXNtXCIgb25TdWJtaXQ9e3N1Ym1pdEhhbmRsZXJ9PlxyXG4gICAgICA8ZGl2IGNsYXNzTmFtZT1cImZsZXggc3BhY2UteC0yXCI+XHJcbiAgICAgICAgPERhdGFFbnRyeVxyXG4gICAgICAgICAgdHlwZT1cInRleHRcIlxyXG4gICAgICAgICAgaWQ9XCJmaXJzdE5hbWVcIlxyXG4gICAgICAgICAgcGxhY2Vob2xkZXI9XCJGaXJzdCBOYW1lXCJcclxuICAgICAgICAgIG9uVGV4dENoYW5nZT17dXBkYXRlVGV4dEhhbmRsZXJ9XHJcbiAgICAgICAgLz5cclxuICAgICAgICA8RGF0YUVudHJ5XHJcbiAgICAgICAgICB0eXBlPVwidGV4dFwiXHJcbiAgICAgICAgICBpZD1cImxhc3ROYW1lXCJcclxuICAgICAgICAgIHBsYWNlaG9sZGVyPVwiTGFzdCBOYW1lXCJcclxuICAgICAgICAgIG9uVGV4dENoYW5nZT17dXBkYXRlVGV4dEhhbmRsZXJ9XHJcbiAgICAgICAgLz5cclxuICAgICAgPC9kaXY+XHJcbiAgICAgIDxEYXRhRW50cnlcclxuICAgICAgICB0eXBlPVwidGV4dFwiXHJcbiAgICAgICAgaWQ9XCJ1c2VybmFtZVwiXHJcbiAgICAgICAgcGxhY2Vob2xkZXI9XCJVc2VybmFtZS9lbWFpbFwiXHJcbiAgICAgICAgb25UZXh0Q2hhbmdlPXt1cGRhdGVUZXh0SGFuZGxlcn1cclxuICAgICAgLz5cclxuICAgICAgPERhdGFFbnRyeVxyXG4gICAgICAgIHR5cGU9XCJwYXNzd29yZFwiXHJcbiAgICAgICAgaWQ9XCJwYXNzd29yZFwiXHJcbiAgICAgICAgcGxhY2Vob2xkZXI9XCJQYXNzd29yZFwiXHJcbiAgICAgICAgb25UZXh0Q2hhbmdlPXt1cGRhdGVUZXh0SGFuZGxlcn1cclxuICAgICAgLz5cclxuXHJcbiAgICAgIDxidXR0b25cclxuICAgICAgICB0eXBlPVwic3VibWl0XCJcclxuICAgICAgICBjbGFzc05hbWU9XCJtdC02IHctZnVsbCBiZy10aGVtZS1ibHVlIHRleHQtd2hpdGUgcHktMiByb3VuZGVkLWxnIGhvdmVyOmJnLXRoZW1lLWxpZ2h0LWJsdWVcIlxyXG4gICAgICA+XHJcbiAgICAgICAgU2lnbiBVcFxyXG4gICAgICA8L2J1dHRvbj5cclxuICAgIDwvZm9ybT5cclxuICApO1xyXG59XHJcbiJdLCJuYW1lcyI6WyJSZWFjdCIsInVzZVN0YXRlIiwiRGF0YUVudHJ5IiwicmVnaXN0ZXIiLCJSZWdpc3RlckZvcm0iLCJmaXJzdE5hbWUiLCJzZXRGaXJzdE5hbWUiLCJsYXN0TmFtZSIsInNldExhc3ROYW1lIiwidXNlcm5hbWUiLCJzZXRVc2VybmFtZSIsInBhc3N3b3JkIiwic2V0UGFzc3dvcmQiLCJzdWJtaXRIYW5kbGVyIiwiZSIsInByZXZlbnREZWZhdWx0IiwiZm9ybURhdGEiLCJsYXN0TkFtZSIsInVzZXJubWFlIiwiY29uc29sZSIsImxvZyIsInJlc3BvbnNlIiwiZXJyb3IiLCJ1cGRhdGVUZXh0SGFuZGxlciIsImVudGVyZWRUZXh0IiwiaWQiLCJmb3JtIiwiY2xhc3NOYW1lIiwib25TdWJtaXQiLCJkaXYiLCJ0eXBlIiwicGxhY2Vob2xkZXIiLCJvblRleHRDaGFuZ2UiLCJidXR0b24iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///(app-pages-browser)/./src/app/register/RegisterForm.tsx\n"));

/***/ })

});