import React, { useRef, useState } from "react";
import { HiPlus, HiMinus } from "react-icons/hi"

type Props = {
  question: string,
  answer: string,
}

export function AccordionItem({ question, answer }: Props) { 
  const contentRef = useRef<HTMLDivElement>(null)

  const [selected, setSelected] = useState(false)

  const toggle = () => {
    setSelected(!selected)
  }

  return (
    <div className="py-3">
      <div className="border-b-4 bg-transparent px-3 py-3 h-14 flex items-center cursor-pointer rounded-[4px]"
      onClick={toggle}>
        <p className="text-white text-xl font-normal">{question}</p>
        <div dir="rtl" className="flex-auto text-xl ms-0">
          {selected ? <HiMinus className="text-xl" /> : <HiPlus className="text-xl" />}
        </div> 
      </div>
      <div className="overflow-hidden transition-all duration-[400ms]"
      ref={contentRef}
      style={selected ? { height: contentRef.current?.scrollHeight + "px" } : { height: "0px" }}>
        <p className="px-3 py-3 leading-normal text-justify font-light">
          {answer}
        </p>
      </div>
    </div>
  )
}