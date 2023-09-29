import { IoArrowForwardCircle } from "react-icons/io5"

export function CategoryCard(props) {
  return (
    <button className="flex flex-row w-full h-28 rounded-md 
    bg-slate-600 items-center pl-16 duration-300 hover:bg-slate-800 
    hover:drop-shadow-xl">
      <div className="text-xl font-semibold">
        { props.category }
      </div>
      <div dir="rtl" className="flex-auto ms-8">
        <IoArrowForwardCircle className="text-2xl"/>
      </div>
    </button>
  )
}