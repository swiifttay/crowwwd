

export default function Card() {
    return <div className="grid-rows-2 bg-white rounded-md transition-transform group-hover:scale-110 duration-200">
        <img src="url" className="row-span-1 rounded-t-md" />
        <div className="row-span-1 p-4">
            <h2 className="font-semibold text-lg">Title</h2>
            <button className="bg-theme-blue text-white px-2 py-1 rounded-full text-xs" />
            <p className="text-sm mt-1">Some description that does not always make sense</p>
        </div>
    </div>
}