import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import Page from "@/app/todo/page";
import { auth0 } from "@/lib/auth0";

describe("Todo一覧", () => {
  afterEach(() => {
    vi.resetAllMocks();
  });

  it("一覧に取得したデータが表示される。", async () => {
    const spy = vi.spyOn(auth0, "getAccessToken").mockImplementation(() =>
      Promise.resolve<{
        token: string;
        expiresAt: number;
      }>({ token: "test", expiresAt: 0 })
    );
    const fetchMock = vi.spyOn(global, "fetch").mockImplementation(
      async () =>
        new Response(
          `[
                    {
                        "id": 1,
                        "title": "テスト1"
                    },
                    {
                        "id": 2,
                        "title": "テスト2"
                    }
                ]`,
          { status: 200 }
        )
    );
    const b = render(await Page());

    expect(spy).toHaveBeenCalled();

    expect(screen.getByText("テスト1")).toBeInTheDocument();
    expect(screen.getByText("テスト2")).toBeInTheDocument();

    expect(fetchMock).toHaveBeenCalledTimes(1);
    expect(fetchMock).toHaveBeenCalledWith("http://backend:8080/todos", {
      cache: "no-store",
      headers: {
        Authorization: "Bearer test",
      },
    });
  });

  it("エラー表示", async () => {
    const spy = vi.spyOn(auth0, "getAccessToken").mockImplementation(() =>
      Promise.resolve<{
        token: string;
        expiresAt: number;
      }>({ token: "test", expiresAt: 0 })
    );

    const fetchMock = vi.spyOn(global, "fetch").mockImplementation(
      async () =>
        new Response(
          `[
                {
                    id: 1,
                    title: 'テスト1'
                },
                {
                    id: 2,
                    title: 'テスト2'
                }
            ]`,
          { status: 400 }
        )
    );
    await expect(async () => {
      render(await Page());
    }).rejects.toThrowError(new Error("データが取得できませんでした。"));
  });
});
