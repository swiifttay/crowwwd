import { useState } from "react";

const data = [
    {
        question: 'Question 1',
        answer: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    },
    {
        question: 'Question 2',
        answer: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
    }
]

export default function FAQ() {
    return (
        <div className="flex flex-col items-center h-fit relative w-full bg-space bg-cover bg-center px-8">
            <div
            id="title"
            className="flex flex-wrap w-full items-center justify-center px-3">
                <h1 className="flex-1 mr-2 py-10 text-center md:text-start text-6xl font-bold">How can we help?</h1>
            </div>
        </div>
    )
}

